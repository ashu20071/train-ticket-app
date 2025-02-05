# Train Ticket Booking Application

## Overview

This Spring Boot REST API application simplifies train ticket booking and management for journeys between London and France.  It handles seat allocation in two 10-seat sections (A and B), enabling users to book, view, modify, and cancel tickets.

## How to start the Applications

1. This application requires ```Java 23```
2. run ``` ./gradlew clean build```
3. to start the app  ``` ./gradlew bootRun```


## Getting Started

To use the API, ensure your system can make HTTP requests to `localhost:8080`. The following sections describe the available endpoints and how to interact with them.

### fetch-all-section-ticket

This API will return all the  seats for a particular section , be it available or not

```bash
curl --location 'localhost:8080/api/v1/fetch/A'
```
sample response:

```json
    {
      "data":
      {
        "seats":
        [
          {
            "seatId": 2,
            "section": "A",
            "ticketId": "6nf5568b-59e6-4a69-9b2b-840f4b3d905d",
            "userInfo":
            {
              "userId": "a12b44b1-6976-430b-ab63-e8d918b3902a",
              "firstName": "B",
              "lastName": "C",
              "email": "ashutosh.singh@gmail.com"
            },
            "isTaken": true
          },
          {
            "seatId": 4,
            "section": "A",
            "isTaken": false
          }
        ]
      },
      "code": "OK"
    }
```
`isTaken` states if the seat is taken or not. if its true it will return the user who has booked it and the corresponding ticket id.

###  book-ticket

This will book the ticket for
1. For a new user
```bash
curl --location 'localhost:8080/api/v1/book-ticket' \
--header 'Content-Type: application/json' \
--data-raw '{
   
    "pricePaid":123,
    "from":"London",
    "to":"France",
    "userInfo":{
        "firstName":"B",
        "lastName":"C",
        "email":"ashutosh.singh@gmail.com"
    }
}'

```
2. An existing user


```bash
curl --location 'localhost:8080/api/v1/book-ticket' \
--header 'Content-Type: application/json' \
--data '{
    "userId":"bb5648f7-4bde-4170-8395-19e88e518ee4",
    "pricePaid":123,
    "from":"London",
    "to":"France"
}'
```

this will return something like

```json
    {
      "data": {
        "userId": "c06039f7-85fc-41e5-b132-37c7cd65e330",
        "from": "London",
        "to": "France",
        "pricePaid": 123,
        "ticketId": "dae141f6-d73d-4191-b6d8-55657f8ec0ed",
        "section": "A",
        "seatNumber": 1
      },
      "code": "OK"
    }
```
1. `userId` specifies the userId under which the ticket is created
2. `ticketId` id of the ticket
3. `section` is the section of the seat
4. `seatNumber` is the seatnumber of the seat



###  user-tickets

Fetches all the ticket fo the user

```bash
curl --location 'localhost:8080/api/v1/ticket/c06039f7-85fc-41e5-b132-37c7cd65e330'

```
this will return something like

```json
    {
      "data": {
        "userId": "c06039f7-85fc-41e5-b132-37c7cd65e330",
        "ticketList": [
          {
            "ticketId": "dae141f6-d73d-4191-b6d8-55657f8ec0ed",
            "section": "A",
            "seatNumber": 1
          }
        ]
      },
      "code": "OK"
    }
```


###  update-ticket-value

API to update the seat info or to disable the ticket


```bash
curl --location --request PUT 'localhost:8080/api/v1/ticket/ddb91223-481d-4230-a05a-5b2b5cf0f35e' \
--header 'Content-Type: application/json' \
--data '{
    "userId":"c06039f7-85fc-41e5-b132-37c7cd65e330",
    "ticketId":"dae141f6-d73d-4191-b6d8-55657f8ec0ed",
    "isEnabled":true,
    "newSection":"A",
    "newSeatNumber":3
}'
```

this will return something like

```json
    {
      "data": true,
      "code": "OK"
    }
```

###  delete-user-ticket

This will delete all the tickets of the user


```bash
curl --location --request DELETE 'localhost:8080/api/v1/ticket/c06039f7-85fc-41e5-b132-37c7cd65e330' \
--data ''

```

this will return something like

```json
    {
  "data": true,
  "code": "OK"
}
```



