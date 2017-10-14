## User Guide

### To run the app:

Build a jar:

`mvn clean package`

Start mysql docker container: 
```
make start_mysql
```
Wait for a couple of seconds for the server to get ready and then start the api server:
```
java -jar target/ApiServer-1.0-SNAPSHOT.jar
```

Make a request to the api: 
```
curl -H "Content-Type: application/json" -X POST -d '{"version":"1.1.0","username":"userA"}' http://localhost:8080/validate
```

The response should be something like this:
```
{"isValid":true,"enabledFeatures":["newfeature","notifications"]}
```

## Dev Guide:

### Unit tests
* `mvn test` to run the tests after you make a change

## Architecture

* Mysql is used as a relational database and is running in docker
* When the application is started some example data are loaded to the database
* The api is designed to support clients that do not provide a username (i.e. username is optional)