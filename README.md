# Orders API

This API manage products and place orders

## Installation

The installation requires [Git](https://git-scm.com/) and 
[Apache Maven](https://maven.apache.org/).

The steps to install are :

```bash
 git clone https://github.com/mmuniz75/VB_order_ms_test.git
```
```bash
 cd VB_order_ms_test
```
```bash
 mvn install
```
```bash
 cd target
```
```bash
 java -jar recruting-test.jar
```

## Documentation

After started the server access the url:
[http://localhost:8080/api/vb/swagger-ui.html](http://localhost:8080/api/vb/swagger-ui.html)

The postman file [orders.postman_collection.json](https://www.getpostman.com/collections/00ba6e1a751d93be5de7) 
has all API's calls examples


## Redundancy
This microservice can be created as a docker's image and deployed in
a Kubernet's server where Pods can be create to attempt the demand

## Authentication
The gateway that router this APIs should expected in the http
header a token where the authorization is checked on a security server