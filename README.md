# Employee Microservices

This is a proof-of-concept application, which demonstrates Microservice Architecture Pattern using Spring Boot Microservices Managed via Spring Cloud, Netflix OSS, ELK Stack, Docker & WS02 APIM.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
In total there are 8 services,

| Core-Services     | Support-Services | 
| ----------------- |------------------| 
| Employee-Deatils  |Eureka-Service    |
| Employee-Salary   |Config-Service    |
| Employee-Rating   |Turbine-Service   |
| Composite-Service |Zipkin-Service    |

All 4 Core-Services are dependent on Support-Services so always run the Support-Services first & then run the Core-Services.

### Prerequisites

What things you need to install the software and how to install them

```
Maven
Java 8
Docker
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

## Running the tests

| Method     | Path | Description |
| ----------------- |------------------| 
|GET  |http://192.168.99.100:{docker-port}/composite/1    | Calls other 3 Services & gives Aggreagted Data|


### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc
