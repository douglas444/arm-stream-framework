# ARM-Stream HTTP

This subproject provides a set of classes that can be used to integrate the ARM-Stream framework into a classifier
through the HTTP protocol. This is specially useful for integrating the framework to a base classifier written in a 
language other than Java.

## How to use it in a project as a _Maven_ dependency

First you need to [install the framework](./../README.md) to your _Maven Local Repository_.

Once you have installed the framework, import the _arm-stream-http_ at your
_Maven_ project by adding the following dependency
to your project's pom.xml file (edit the version if necessary):

```xml
<dependency>
    <artifactId>arm-stream-http</artifactId>
    <groupId>br.ufu.facom.armstream</groupId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```