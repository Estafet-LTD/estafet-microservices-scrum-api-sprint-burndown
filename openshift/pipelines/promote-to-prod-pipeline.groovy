@NonCPS
def getVersions(json) {
	def tags = new groovy.json.JsonSlurper().parseText(json).status.tags
	def versions = []
	for (int i = 0; i < tags.size(); i++) {
		versions << tags[i]['tag']
	}
	return versions
}

@NonCPS
def getPassive(json) {
	def matcher = new groovy.json.JsonSlurper().parseText(json).spec.to.name =~ /(green|blue)(basic\-ui)/
	String namespace = matcher[0][1]
	return namespace.equals("green") ? "blue" : "green" 
}

def recentVersion( versions ) {
	def size = versions.size()
	return versions[size-1]
}

def getLatestVersion(project, microservice) {
	sh "oc get is ${microservice} -o json -n ${project} > image.json"
	def image = readFile('image.json')
	def versions = getVersions(image)
	if (versions.size() == 0) {
		error("There are no images for ${microservice}")
	}
	return recentVersion(versions)
}

node("maven") {
	
	def project = "prod"
	def microservice = "sprint-burndown"
	def version
	def env

	properties([
	  parameters([
	     string(name: 'GITHUB'),
	  ])
	])
	
	stage("determine the environment to deploy to") {
		sh "oc get route basic-ui -o json -n ${project} > route.json"
		def route = readFile('route.json')
		env = getPassive(route)
		println "the target environment is $env"
	}
	
	stage("determine which image is to be deployed") {
		version = getLatestVersion project, microservice
		println "latest version is $version"
	}
	
	stage("checkout release version") {
		checkout scm: [$class: 'GitSCM', 
      userRemoteConfigs: [[url: "https://github.com/${params.GITHUB}/estafet-microservices-scrum-api-sprint-burndown"]], 
      branches: [[name: "refs/tags/${version}"]]], changelog: false, poll: false
	}
	
	stage("ensure the exists database") {
		withMaven(mavenSettingsConfig: 'microservices-scrum') {
	      sh "mvn clean package -P create-prod-db -Dmaven.test.skip=true -Dproject=${project}"
	    } 
	}		
	
	stage("create deployment config") {
		sh "oc process -n ${project} -f openshift/templates/${microservice}-config.yml -p NAMESPACE=${project} -p ENV=${env} -p DOCKER_NAMESPACE=${project} -p DOCKER_IMAGE_LABEL=${version} | oc apply -f -"
		sh "oc set env dc/${env}${microservice} JBOSS_A_MQ_BROKER_URL=tcp://broker-amq-tcp.mq-${env}.svc:61616 JAEGER_AGENT_HOST=jaeger-agent.${project}.svc JAEGER_SAMPLER_MANAGER_HOST_PORT=jaeger-agent.${project}.svc:5778 JAEGER_SAMPLER_PARAM=1 JAEGER_SAMPLER_TYPE=const -n ${project}"	
	}
	
	stage("execute deployment") {
		openshiftVerifyDeployment namespace: project, depCfg: "${env}${microservice}", replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000" 
	}

}

