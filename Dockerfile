FROM eclipse-temurin:11-jdk

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw && ./mvnw clean install

CMD ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.MinasTightIntegration" -Dexec.args="output/minas_tight_integration" && \
    ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.EchoTightIntegration" -Dexec.args="output/echo_tight_integration" && \
    ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.MinasWithoutArmStream" -Dexec.args="output/minas_raw" && \
    ./mvnw exec:java -pl arm-stream-exp -Dexec.mainClass="br.ufu.facom.armstream.exp.main.EchoWithoutArmStream" -Dexec.args="output/echo_raw"