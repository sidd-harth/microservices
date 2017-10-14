# Employee Microservices

This is a proof-of-concept application, which demonstrates Microservice Architecture Pattern using Spring Boot Microservices Managed via Spring Cloud, Netflix OSS, ELK Stack, Docker & WS02 APIM.

![micro21](https://user-images.githubusercontent.com/28925814/31579573-b1de33e6-b156-11e7-97cf-fb549ae7ef20.png)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
In total there are 8 services,

| Core-Services     | Support-Services | Log Analysis | 
| ----------------- |------------------|--------------| 
| Employee-Deatils  |Eureka-Service    |Elastic Search|
| Employee-Salary   |Config-Service    |Logstash|
| Employee-Rating   |Turbine-Service   |Kibana|
| Composite-Service |Zipkin-Service    |Zipkin|

All 4 Core-Services are dependent on Support-Services so always run the Support-Services first & then run the Core-Services.

### Prerequisites

What things you need to install the software and how to install them

```
Maven
Java 8
Docker needs to run on host 192.168.99.100
WS02 API Manager
```

### Running in Docker

Use docker-compose files to start the services within Docker container.

Download the docker-compose files, within Docker run below CMD, one for ELK Compose YAML.

```
docker-compose up -d
```

And repeat same for Support/Core-Service YAML.

```
docker-compose up -d
```
Once everythings is done, you should be able to see somrthing like this,
![screenshot_1](https://user-images.githubusercontent.com/28925814/31579483-08951ddc-b155-11e7-9a0a-035d3bae61d9.jpg)

Open Browser & hit following URLs,
| Service      | Path                              | Description             |
| -------------|-----------------------------------|-------------------------|
|Eureka        |http://192.168.99.100:8761/eureka  | Eureka with all Services|
|Turbine       |http://192.168.99.100:8989/hystrix | Info on Circuit Breakers|
|Zipkin        |http://192.168.99.100:9411         | Latence Details         |
|Kibana        |http://192.168.99.100:5601         | Log Analysis            |

## Running the tests

| Method            | Path                                              | Description                                   |
| ----------------- |---------------------------------------------------|-----------------------------------------------|
|GET                |http://192.168.99.100:{docker-port}/composite/1    | Calls other 3 Services & gives Aggreagted Data|
|GET                |http://192.168.99.100:{docker-port}/zipkin-test    | Check Zipkin UI|


## Versioning

This is just a PoC & hence no versioning is followed as of now.

## Authors

* **Siddharth Barahalikar** 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
