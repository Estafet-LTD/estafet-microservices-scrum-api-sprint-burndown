mvn clean install -P local
cp target/estafet-microservices-scrum-api-sprint-burndown-*.war $WILDFLY_INSTALL/standalone/deployments
