chmod +x ./mvnw && ./mvnw clean install
MAVEN_OPTS="-Xms2G -Xmx8G" ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.MinasLooseIntegration" -Dexec.args="output/minas_loose_integration"
sleep 10
MAVEN_OPTS="-Xms2G -Xmx8G" ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.EchoLooseIntegration" -Dexec.args="output/echo_lose_integration"
sleep 10