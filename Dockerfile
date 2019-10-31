FROM openjdk:8
COPY chat-graph*.jar /opt/chat-graph.jar
COPY application.properties /opt/
CMD mkdir -p /opt/scenarios/
COPY graph.json /opt/
COPY graph.json /opt/scenarios/
WORKDIR /opt
CMD java -jar /opt/chat-graph.jar --spring.config.additional-location=/opt/application.properties
