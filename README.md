# ARM-Stream Framework

ARM-Stream: Active Recovery of Miscategorizations in Clustering-Based Data Stream Classifiers.

### Project requirements

* Java 11

### Links that may be useful

* [Download JDK 11](https://adoptium.net/)
* [Apache Maven Tutorial](https://www.baeldung.com/maven)
* [Multi-Module Project with Maven](https://www.baeldung.com/maven-multi-module)

### About each subproject

* [ARM-Stream API](arm-stream-api/README.md)
* [ARM-Stream Core](arm-stream-core/README.md)
* [ARM-Stream Reference Implementations](arm-stream-impl/README.md)
* [ARM-Stream Reference Experiments](arm-stream-exp/README.md)
* [ARM-Stream HTTP](arm-stream-http/README.md)

### How to build and install?

To build and install the project, execute the following command from the root directory of the project:

```
.\mvnw.cmd clean install -DskipTests
```

### How to run?

To run one of the reference experiments, choose the respective command line and execute it from the root of
the project. The output files will be created in the `output` directory in the root of the project. If the
directory does not exist yet, it will be created. You can specify a different destination for the output files by
editing the `-Dexec.args` argument in the command line.

#### _ARM-MINAS Loose Integration_

```
.\mvnw.cmd exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.MinasLooseIntegration" -D"exec.args"="output/minas_loose_integration"
```

#### _ARM-ECHO Loose Integration_

```
.\mvnw.cmd exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.EchoLooseIntegration" -D"exec.args"="output/echo_loose_integration"
```

#### _ARM-CDSCAL Loose Integration_

Make sure that you have the [CDSC-AL](https://github.com/douglas444/arm-remote-cdsc-al) server running and then execute the command line bellow.

```
.\mvnw.cmd exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.CdscalLooseIntegration" -D"exec.args"="output/cdscal_loose_integration"
```

#### _ARM-MINAS Tight Integration_:

```
.\mvnw.cmd exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.MinasTightIntegration" -D"exec.args"="output/minas_tight_integration"
```

#### _ARM-MINAS Threshold Factor Analysis_:

```
.\mvnw.cmd exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.MinasThresholdFactorAnalysis" -D"exec.args"="output/minas_threshold_factor_analysis"
```

#### _ARM-ECHO Threshold Factor Analysis_:

```
.\mvnw.cmd exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.EchoThresholdFactorAnalysis" -D"exec.args"="output/echo_threshold_factor_analysis"
```

#### _ARM-CDSCAL Threshold Factor Analysis_:

Make sure that you have the [CDSC-AL](https://github.com/douglas444/arm-remote-cdsc-al) server running and then execute the command line bellow.

```
.\mvnw.cmd exec:java -pl arm-stream-exp -D"exec.mainClass"="br.ufu.facom.armstream.exp.main.CdscalThresholdFactorAnalysis" -D"exec.args"="output/cdscal_threshold_factor_analysis"
```

### Where are the datasets used in the reference experiments?

The datasets used in the reference experiments can be found in
the [arm-stream-exp/src/main/resources](arm-stream-exp/src/main/resources) directory.
