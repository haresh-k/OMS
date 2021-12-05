# OMS
Order management system with payment tracking for customers.

## Installation
The project uses [gradle](https://gradle.org/) 6.0.1v for building the project. Use the same version of gradle to avoid library compatibility issues. Download 6.0.1v [here](https://gradle.org/next-steps/?version=6.0.1&format=bin)

## Usage
Post installation of gradle, cd into the project root directory and run
```bash
gradle clean build
```
The above command builds the jar under `builds/libs/` folder. Run
```bash
java -jar build/libs/OMS-1.0.0.jar
```

## Extras
* If port `8083` is being used already, change the `server.port` in `application.properties` file. 
* Use [api-docs](http://localhost:8083/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/) to go through the available APIs.

## License
No licensing until now.