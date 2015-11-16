# spring-boot-ssl-truststore-gen [![Build Status](https://travis-ci.org/Orange-OpenSource/truststore-generator.svg?branch=master)](https://travis-ci.org/Orange-OpenSource/truststore-generator)


## Feature

Provides spring boot application with a [java SSL truststore](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html#CustomizingStores) made up of :
 * default truststore CA certificates
 * additional CA certificates extracted from a custom <i>TRUSTSTORE</i> System property

The [java SSL truststore](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html#CustomizingStores) will be accessible through <i>javax.net.ssl.trustStore</i> and <i>javax.net.ssl.trustStorePassword</i> system properties.

## Details

spring-boot-ssl-truststore-gen will register a [spring boot event listener](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-spring-application.html#boot-features-application-events-and-listeners) that reacts whenever an [ApplicationStartedEvent](http://docs.spring.io/autorepo/docs/spring-boot/1.2.0.M2/api/org/springframework/boot/context/event/ApplicationStartedEvent.html) is sent (at the start of a run, but before any processing except the registration of listeners and initializers).

## Building

To build the source you will need to install JDK 1.8.

spring-boot-ssl-truststore-gen uses Maven

```
$ ./mvn install
```

## Usage

To enable automatic truststore generation, all you need is to add spring-boot-ssl-truststore-gen dependency to you spring boot application.

Example for maven

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-ssl</artifactId>
            <version>0.1-SNAPSHOT</version>
        </dependency>
```

and set a <i>TRUSTSTORE</i> System property with a [json](https://en.wikipedia.org/wiki/JSON) chain containing CA Certificates to add to default java trustore

```
$ export TRUSTSTORE=<JSON_TRUSTSTORE_VALUE>
```

Here is a sample of <JSON_TRUSTSTORE_VALUE> content :

{"certificates":["-----BEGIN CERTIFICATE-----\r\nMIIDhzCCAm+gAwIBAgIEYmqHlTANBgkqhkiG9w0BAQsFADB0MRAwDgYDVQQGEwdVbmtub3duMRAwDgYDVQQIEwdVbmtub3duMRAwDgYDVQQHEwdVbmtub3duMRYwFAYDVQQKEw13b3JsZCBjb21wYW55MRAwDgYDVQQLEwdVbmtub3duMRIwEAYDVQQDEwlqb2huIHBhdWwwHhcNMTUxMDI5MTQzNjEwWhcNMTYwMTI3MTQzNjEwWjB0MRAwDgYDVQQGEwdVbmtub3duMRAwDgYDVQQIEwdVbmtub3duMRAwDgYDVQQHEwdVbmtub3duMRYwFAYDVQQKEw13b3JsZCBjb21wYW55MRAwDgYDVQQLEwdVbmtub3duMRIwEAYDVQQDEwlqb2huIHBhdWwwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC+UGMvPPnJowZcE5KI+FSyg8kCJtXLAK59e9JqMnbzzJUX3RQfT2BH08xN0z+cGdqOQNV7gvf2TCEJYOwFqB60JEhIgNPXGY/xOcFHY7qm+5MMXSvkxPw4yCEFj1vxfGY8kBKXWknhmE2eXG2S+bVSmwo9IBOHXgFzhOqmQly1uLP1x06NtpJV9lTWHBECWa7fIBmMUkXCrxdqVJb/OFjkjrmBhFjYhjTi+syqxO/blQiDDfGlOGTvf37ivcUtXQIvH2qce2vQuP+iZA/f5levMdySa6+Vdfdi114V83HjAsJGWStz0K2W5QRw/3ilw2D0hyCRKavOQBtG5m+o3v29AgMBAAGjITAfMB0GA1UdDgQWBBTe/Jg26TgrkhLLWBMH vinQzM4r0DANBgkqhkiG9w0BAQsFAAOCAQEAC7I3O4qNGF8KfWvJYXAcTW3cRTTzctEqaZvkR7biNoyhT6FykuCEgmrKId6HSaOCQEHp8h9/IHh/pwWFFNrIBCsPbyZBggTKC2Hj/dna/T7Ejoqsg3pXytDIlnDSPi3vsUcyLMpC1qZKRk5mYto6fxsb48IcFTyytQygcdvcYgGe5yQasYL4s55k9whwNbrzYHaWU3uNc3UVjyxkKAufrOQdWktg hIGlTE8Wm4gNNZx116hbCyFmK7UKOufRyW0pF1UcicfkaPs4Dd1ApU79uifvvN9PmjPkk88buTsMqzvkfey8HBaoZb9AiVYPn2if8HINvCOKaaLe7ixzgBGNkg==\r\n-----END CERTIFICATE-----"]}

