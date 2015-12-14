# spring-boot-ssl-truststore-gen [![Build Status](https://travis-ci.org/Orange-OpenSource/spring-boot-ssl-truststore-gen.svg?branch=master)](https://travis-ci.org/Orange-OpenSource/spring-boot-ssl-truststore-gen)


## Feature

Provides spring boot application with a [java SSL truststore](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html#CustomizingStores) made up of :
 * default truststore CA certificates
 * additional CA certificate extracted from a custom <i>TRUSTED_CA_CERTIFICATE_VALUE</i> System property

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
                <groupId>com.orange.clara.cloud.boot.ssl-truststore-gen</groupId>
                <artifactId>spring-boot-ssl-truststore-gen</artifactId>
                <version>2.0.21</version>
        </dependency>
```

and set a <i>TRUSTED_CA_CERTIFICATE</i> System property with a String chain containing trusted CA Certificate to add to default java truststore

```
$ export TRUSTED_CA_CERTIFICATE=<TRUSTED_CA_CERTIFICATE_VALUE>
```

Here is a sample of <TRUSTED_CA_CERTIFICATE_VALUE> content :

```
-----BEGIN CERTIFICATE-----
MIIDhzCCAm+gAwIBAgIEYmqHlTANBgkqhkiG9w0BAQsFADB0MRAwDgYDVQQGEwdV
bmtub3duMRAwDgYDVQQIEwdVbmtub3duMRAwDgYDVQQHEwdVbmtub3duMRYwFAYD
VQQKEw13b3JsZCBjb21wYW55MRAwDgYDVQQLEwdVbmtub3duMRIwEAYDVQQDEwlq
b2huIHBhdWwwHhcNMTUxMDI5MTQzNjEwWhcNMTYwMTI3MTQzNjEwWjB0MRAwDgYD
VQQGEwdVbmtub3duMRAwDgYDVQQIEwdVbmtub3duMRAwDgYDVQQHEwdVbmtub3du
MRYwFAYDVQQKEw13b3JsZCBjb21wYW55MRAwDgYDVQQLEwdVbmtub3duMRIwEAYD
VQQDEwlqb2huIHBhdWwwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC+
UGMvPPnJowZcE5KI+FSyg8kCJtXLAK59e9JqMnbzzJUX3RQfT2BH08xN0z+cGdqO
QNV7gvf2TCEJYOwFqB60JEhIgNPXGY/xOcFHY7qm+5MMXSvkxPw4yCEFj1vxfGY8
kBKXWknhmE2eXG2S+bVSmwo9IBOHXgFzhOqmQly1uLP1x06NtpJV9lTWHBECWa7f
IBmMUkXCrxdqVJb/OFjkjrmBhFjYhjTi+syqxO/blQiDDfGlOGTvf37ivcUtXQIv
H2qce2vQuP+iZA/f5levMdySa6+Vdfdi114V83HjAsJGWStz0K2W5QRw/3ilw2D0
hyCRKavOQBtG5m+o3v29AgMBAAGjITAfMB0GA1UdDgQWBBTe/Jg26TgrkhLLWBMH
vinQzM4r0DANBgkqhkiG9w0BAQsFAAOCAQEAC7I3O4qNGF8KfWvJYXAcTW3cRTTz
ctEqaZvkR7biNoyhT6FykuCEgmrKId6HSaOCQEHp8h9/IHh/pwWFFNrIBCsPbyZB
ggTKC2Hj/dna/T7Ejoqsg3pXytDIlnDSPi3vsUcyLMpC1qZKRk5mYto6fxsb48Ic
FTyytQygcdvcYgGe5yQasYL4s55k9whwNbrzYHaWU3uNc3UVjyxkKAufrOQdWktg
hIGlTE8Wm4gNNZx116hbCyFmK7UKOufRyW0pF1UcicfkaPs4Dd1ApU79uifvvN9P
mjPkk88buTsMqzvkfey8HBaoZb9AiVYPn2if8HINvCOKaaLe7ixzgBGNkg==
-----END CERTIFICATE-----
```
