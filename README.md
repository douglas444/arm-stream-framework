# ARM-Stream Framework
ARM-Stream: Active Recovery of Miscategorizations in Clustering-Based Data Stream Classifiers.

### Project requirements

* Java 8
* Apache Maven 3.8.6

### Links that may be useful

* [How to install Java](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
* [Java Tutorial](https://www.baeldung.com/java-tutorial)
* [Java 8 New Features](https://www.baeldung.com/java-8-new-features)
* [How to install Apache Maven](https://www.baeldung.com/install-maven-on-windows-linux-mac)
* [Apache Maven Tutorial](https://www.baeldung.com/maven)
* [Multi-Module Project with Maven](https://www.baeldung.com/maven-multi-module)

### About each subproject

* [ARM-Stream API](arm-stream-api/README.md)
* [ARM-Stream Core](arm-stream-core/README.md)
* [ARM-Stream Reference Implementations](arm-stream-impl/README.md)
* [ARM-Stream Reference Experiments](arm-stream-exp/README.md)

### How to build and install?

To build and install the project, execute the following command from the root directory of the project:

```
mvn clean install
```

### How to run?

To run one of the reference experiments, choose the respective command line and execute it from the root directory of the project. The output files will be created in the `output` directory in the root directory of the project. If the directory does not exist yet, it will be created. You can specify a different destination for the output files by editing the `-Dexec.args` argument in the command line.
  
#### _ARM-MINAS Loose Integration_
```
mvn exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.MinasLooseIntegration" -D"exec.args"="output/minas_loose_integration"
```

#### _ARM-ECHO Loose Integration_
```
mvn exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.EchoLooseIntegration" -D"exec.args"="output/echo_loose_integration"
```

#### _ARM-MINAS Tight Integration_:
```
mvn exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.MinasTightIntegration" -D"exec.args"="output/minas_tight_integration"
```

#### _ARM-MINAS Threshold Factor Analysis_:
```
mvn exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.MinasThresholdFactorAnalysis" -D"exec.args"="output/minas_threshold_factor_analysis"
```

#### _ARM-ECHO Threshold Factor Analysis_:
```
mvn exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.EchoThresholdFactorAnalysis" -D"exec.args"="output/echo_threshold_factor_analysis"
```

### Where are the datasets used in the reference experiments?

The datasets used in the reference experiments can be found in the [arm-stream-exp/src/main/resources](arm-stream-exp/src/main/resources) directory.
