# Code Challenge Solution
    This is assignment solution for HSBC code challenge. The app was developed in java using Spring Boot.
    
# Requirements
* Maven 3.3.3
* Java 1.8

## Run
`mvn spring-boot:run` starts application on localhost:8080

## Build application
`mvn clean package -U`

## Run tests
`mvn clean verify`

## Posting


### POST http://localhost:8080/api/v1/posts

Request body:

    {
    	"username":"Alice",
    	"message":"Sample message"
    }

#### Http status code
* 201 when message post has been created.
* 400 when body is invalid e.g message length exceeds 140 chars.

## Wall

### GET http://localhost:8080/api/v1/posts/{username}

Response body:

    [
        {
            "username": "Bob",
            "message": "Bob 2 message",
            "createdAt": "2020-07-07 18:58:11"
        },
        {
            "username": "Bob",
            "message": "Bob 1 message",
            "createdAt": "2020-07-07 18:57:06"
        }
    ]

#### Http status code
* 200 when all posts are returned.
* 400 when username does not exist.
    
## Following

### POST http://localhost:8080/api/v1/users/follow

Request body:

    {
    	"username":"Alice",
    	"followUsername":"Bob"
    }
 
 #### Http status code
 * 200 when username successfully follows followUsername.
 * 400 when username or followUsername does not exist.   
 
 ## Timeline
 
 #### GET http://localhost:8080/api/v1/posts/followed/{username}

Response body:

    [
        {
            "username": "Alice",
            "message": "Alice 2 message",
            "createdAt": "2020-07-07 18:58:25"
        },
        {
            "username": "Alice",
            "message": "Alice 1 message",
            "createdAt": "2020-07-07 18:58:19"
        },
        {
            "username": "Bob",
            "message": "Bob 2 message",
            "createdAt": "2020-07-07 18:54:11"
        },
        {
            "username": "Bob",
            "message": "Bob 1 message",
            "createdAt": "2020-07-07 18:54:06"
        }
    ]
    
 #### Http status code
* 200 when all posts are returned.
* 400 when username does not exist.