FROM openjdk:8
COPY chat-graph*.jar /opt/chat-graph.jar
COPY graph_exec.json /opt/
COPY application.properties /opt/
WORKDIR /opt
CMD mkdir keystore
COPY /home/podkmax/certs/chat-graph/voice-ivr.p12 /opt/keystore/
CMD java -jar /opt/chat-graph.jar --spring.config.additional-location=/opt/application.properties
