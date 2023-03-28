# ARM-Stream Core

This subproject contains the classes required to integrate, run and evaluate base classifiers, 
meta-categorizers and active-categorizers implemented using the [Arm-Stream API](./../arm-stream-api/README.md).
Two integration types are available: loose and tight integration. Examples on how to use the ARM-Stream Core can be 
found at the [Arm-Stream Reference Experiments](./../arm-stream-exp/README.md) subproject.

## Maven Dependencies

* [Arm-Stream API](./../arm-stream-api/README.md)

## How to use it in a project as a _Maven_ dependency

First you need to [install the framework](./../README.md) to your _Maven Local Repository_.

Once you have installed the framework, import the _arm-stream-core_ at your
_Maven_ project by adding the following dependency
to your project's pom.xml file (edit the version if necessary):

```xml
<dependency>
    <artifactId>arm-stream-core</artifactId>
    <groupId>br.ufu.facom.armstream</groupId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
