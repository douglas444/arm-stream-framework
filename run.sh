chmod +x ./mvnw && ./mvnw clean install
MAVEN_OPTS="-Xms2G -Xmx8G" ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.MinasTightIntegration" -Dexec.args="output/minas_tight_integration"
sleep 10
MAVEN_OPTS="-Xms2G -Xmx8G" ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.EchoTightIntegration" -Dexec.args="output/echo_tight_integration"
sleep 10
MAVEN_OPTS="-Xms2G -Xmx8G" ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.MinasWithoutArmStream" -Dexec.args="output/minas_raw"
sleep 10
MAVEN_OPTS="-Xms2G -Xmx8G" ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.EchoWithoutArmStream" -Dexec.args="output/echo_raw"
sleep 10