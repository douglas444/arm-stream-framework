# ARM-Stream Reference Experiments

This subproject uses the [reference implementations](./../arm-stream-impl) to provide examples of how to configure and perform experiments using the framework. Instructions on how to execute the experiments can found at the 
[README.md of the parent project](./../README.md). The parameter configurations used in the experiments are available in this [class](./src/main/java/br/ufu/facom/armstream/exp/workspace/ExperimentWorkspace.java). The datasets used in the experiments can be found [here](./src/main/resources).

## Maven Dependencies

* [Arm-Stream API](./../arm-stream-api/README.md)
* [Arm-Stream Core](./../arm-stream-core/README.md)
* Jackson Databind (Available at the _Maven Central Repository_)

Dependencies available at the _Maven Central Repository_ will be downloaded automatically during the
[build](./../README.md).

## How to use it in a project as a _Maven_ dependency

First you need to [install the framework](./../README.md) to your _Maven Local Repository_.

Once you have installed the framework, import the _arm-stream-exp_ at your
_Maven_ project by adding the following dependency
to your project's pom.xml file (edit the version if necessary):

```xml
<dependency>
    <artifactId>arm-stream-exp</artifactId>
    <groupId>br.ufu.facom.armstream</groupId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
