FROM openjdk:8
COPY chat-graph*.jar /opt/chat-graph.jar
COPY application.properties /opt/
COPY graph.json /opt/
WORKDIR /opt
CMD mkdir -p /opt/src/main/resources/scenarios/
COPY graph.json /opt/src/main/resources/scenarios/
CMD java -jar /opt/chat-graph.jar --spring.config.additional-location=/opt/application.properties