# Estafet Microservices Scrum Sprint Burndown Report
Aggregates data from various events to produce sprint burndown reports.
## What is this?
This application is a microservice that produces a burndown report for a sprint when supplied with a sprint id.

Each microservice has it's own git repository, but there is a master git repository that contains links to all of the repositories [here](https://github.com/Estafet-LTD/estafet-microservices-scrum).
## Getting Started
You can find a detailed explanation of how to install this (and other microservices) [here](https://github.com/Estafet-LTD/estafet-microservices-scrum#getting-started).
## API Interface

### Sprint Burndown JSON object

```json
{
    "id": 1,
    "startDate": "2017-10-01 00:00:00",
    "number": 1,
    "noDays": 5,
    "sprintDays": [
        {
            "id": 1,
            "dayNo": 1,
            "hoursTotal": 21,
            "sprintDay": "2017-10-02 00:00:00"
        },
        {
            "id": 2,
            "dayNo": 2,
            "hoursTotal": 0,
            "sprintDay": "2017-10-03 00:00:00"
        },
        {
            "id": 3,
            "dayNo": 3,
            "hoursTotal": 0,
            "sprintDay": "2017-10-04 00:00:00"
        },
        {
            "id": 4,
            "dayNo": 4,
            "hoursTotal": 0,
            "sprintDay": "2017-10-05 00:00:00"
        },
        {
            "id": 5,
            "dayNo": 5,
            "hoursTotal": 0,
            "sprintDay": "2017-10-06 00:00:00"
        }
    ]
}
```

### Restful Operations

To retrieve an example the project burndown object (useful for testing to see the microservice is online).

```
GET http://sprint-burndown/burndown
```

To retrieve a sprint burndown a project that has an id = 1

```
GET http://sprint-burndown/sprint/1/burndown
```

## Environment Variables
```
JBOSS_A_MQ_BROKER_URL=tcp://localhost:61616
JBOSS_A_MQ_BROKER_USER=estafet
JBOSS_A_MQ_BROKER_PASSWORD=estafet

SPRINT_BURNDOWN_JDBC_URL=jdbc:postgresql://localhost:5432/project-burndown
SPRINT_BURNDOWN_DB_USER=postgres
SPRINT_BURNDOWN_DB_PASSWORD=welcome1

SPRINT_API_SERVICE_URI=http://localhost:8080/sprint-api
STORY_API_SERVICE_URI=http://localhost:8080/story-api
TASK_API_SERVICE_URI=http://localhost:8080/task-api
```

