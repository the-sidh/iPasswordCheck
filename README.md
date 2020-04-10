# iPasswordCheck challenge

backend challenge


# Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing purposes. 

## Running

Use gradle to run the application

```
./gradlew run
```
# API endpoints

This endpoint allow you to validate if a given password follows the formatting rules defined by the challenge.
There is a [a Postman collection](https://github.com/the-sidh/iPasswordCheck/blob/master/postman/iPasswordCheck.postman_collection.json) supplied with the code that has sample request for convenience.

## POST
`Validate a password` [/validate] <br/>
### Request

`POST /validate /`

   curl -X POST \
  http://localhost:7000/validate \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 33' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:7000' \
  -H 'Postman-Token: a256fa2b-d48a-4bd0-af39-9394181b41c7,1b1f1f34-e0e6-433a-8abb-341c27cfcf44' \
  -H 'User-Agent: PostmanRuntime/7.19.0' \
  -H 'cache-control: no-cache' \
  -d '{
    "password": "A12345678d;"
}'
### Response

   {
    "valid": true
}
___

# Running the tests

The code was done relying heavily on TDD, so we have a comprehensive code coverage.
You can run the unit tests using gradle
```
gradle test
```
Automatic end-to-end testing is supplied by component testing, that evaluates the business rules supplied on the challenge. You can run the components tests also by using gradle
```
gradle componentTest
```

## Integration tests

Integration tests can be achieved by using the postman collection supplied with the code.
![testResults](https://github.com/the-sidh/iPasswordCheck/blob/master/integratedTestsResult.png)

# Code discussion 

Despite been a challenge based a very simple set of rules, I believe it achieves the goal to provide good discussion on technical joices. I'll disgress about some of those decisions on the sections bellow

## On the domain design

## On the unit tests
Using TDD I had a good test coverage, what can be 

## On the component tests
Component tests should evaluate if the application behavior as a whole is acting as expected. It covers few bussiness rules, but is concerned if the apropriate response is provided, such as correct HTTP statuses and well formatted response.
I used mockServer to run the server and restAssured to assert the results.
It was not the case with the current challenge, but a service usually depends uppon consuming external resources, such as other APIs. If that was the case, those external APIs should be mocked.
This is a import difference amoung component and integrated tests. The second runs in the realease phase, and should consume actual external resources.

## On the integrated tests
As stated before, I chose to do integrated tests using a Postman collection.
It may be argued it is not be the best solution because there is no garantee that the colleaction will be updated when a new funcionality is released. I believe that it is not entirely true. The execution of the tests should be part of the release proccess and ensuring that the collection is up-to-date should be a concearn of entire team, and a obrigatory concern of QA.
I think that the discipline of BDD (behavior driven development) has great benefits, but is hard to be fully implemented for some reasons as:
* hard to implement frameworks, such as cucumber
* depends product and bussiness teams feeling comfortable to deal with code

I believe that maintaing a clear and concise collection we can achieve the same benefits that following a BDD aproach. We have a indepennt platform that will break if any bussiness rule is not implemented and provides easy to understand and graphic interface, allowing to use natural language to describe the tests and the results.


## On the validation
I know that regular expression may sometimes be criptic, not only to other developers who inherit a code base, but even to the original author after some time. That was the reason I chose to isolate each particular rule in a String constant on the service class, thus avoiding creating a complex "magic number" RE.

```
const val SPECIAL_CHARACTER = "(?=.*[^a-zA-Z0-9 ])"
const val DIGITS = """(?=.*\d)"""
const val LOWER_CASE = "(?=.*[a-z])"
const val UPPER_CASE = "(?=.*[A-Z])"
const val MINIMUM_SIZE = ".{9}"
```

## On the joice of the web framework

I chose [Javalin](http://https://javalin.io/), a lightweight web framework that I have been using lately. 

The learning curve is flat and the amount of boiler plate code tends to be among the lower for java or kotlin web frameworks. 
It's style resembles Express.

It runs over a jetty server.

# Built With

* [Kotlin](https://kotlinlang.org/) - The programming language that does more with less code
* [Javalin](http://https://javalin.io/) - A lightweight web framework for Java and Kotlin
* [Koin](https://insert-koin.io/) - Dependency injection for Kotlin
* [Gradle](https://gradle.org/) - More than just a building toolkit

# Author

* **Sidharta Rezende** 



