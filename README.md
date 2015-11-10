# spring-boot-ssl-truststore-gen [![Build Status](https://travis-ci.org/Orange-OpenSource/truststore-generator.svg?branch=master)](https://travis-ci.org/Orange-OpenSource/truststore-generator)


## Features

* SSL trust store generation on spring boot application startup

## Building

To build the source you will need to install JDK 1.8.

spring-boot-ssl-truststore-gen uses Maven

```
$ ./mvn install
```

## Using spring-boot-ssl-truststore-gen

To enable truststore generation, all you need is to add spring-boot-ssl-truststore-gen dependency to you spring boot application.

Example for maven

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-ssl</artifactId>
            <version>0.1-SNAPSHOT</version>
        </dependency>
```

