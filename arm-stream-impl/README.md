# ARM-Stream Reference Implementations

This subproject contains reference implementations for the interfaces defined by the
[arm-stream-api](./../arm-stream-api/README.md). The available implementations are listed bellow.

### _Base classifiers_

* [MINAS](./src/main/java/br/ufu/facom/armstream/ref/minas/armstream/ArmMinas.java)
* [ECHO](./src/main/java/br/ufu/facom/armstream/ref/echo/armstream/ArmEcho.java)
* [CDSC-AL](./src/main/java/br/ufu/facom/armstream/ref/cdscal/ArmCdscal.java)

### _Meta-Categorizers_

* [NNCR](./src/main/java/br/ufu/facom/armstream/ref/categorizers/meta/NNCR.java)
* [NNAR](./src/main/java/br/ufu/facom/armstream/ref/categorizers/meta/NNAR.java)
* [NDNCR](./src/main/java/br/ufu/facom/armstream/ref/categorizers/meta/NDNCR.java)
* [NDNAR](./src/main/java/br/ufu/facom/armstream/ref/categorizers/meta/NDNAR.java)

All the meta categorizers above extend the
same [parent class](./src/main/java/br/ufu/facom/armstream/ref/categorizers/meta/BayesErrorCategorizer.java), which
defines a generic bayes error categorizer and implements the meta-categorizer interface.

### _Active-Categorizers_

* [MKR](./src/main/java/br/ufu/facom/armstream/ref/categorizers/active/MKR.java)
* [MKCN](./src/main/java/br/ufu/facom/armstream/ref/categorizers/active/MKCN.java)
* [Dummy](./src/main/java/br/ufu/facom/armstream/ref/categorizers/active/Dummy.java) (used when the active-categorizer's
  results is not relevant, like in the threshold analysis experiments).

## Maven Dependencies

* [Arm-Stream API](./../arm-stream-api/README.md)
* Apache Commons Math (Available at the _Maven Central Repository_)
* JUnit Jupiter API (Available at the _Maven Central Repository_)

Dependencies available at the _Maven Central Repository_ will be downloaded automatically during the
[build](./../README.md).

## How to use it in a project as a _Maven_ dependency

First you need to [install the framework](./../README.md) to your _Maven Local Repository_.

Once you have installed the framework, import the _arm-stream-impl_ at your
_Maven_ project by adding the following dependency
to your project's pom.xml file (edit the version if necessary):

```xml
<dependency>
    <artifactId>arm-stream-impl</artifactId>
    <groupId>br.ufu.facom.armstream</groupId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
