FROM openjdk:8
COPY chat-graph*.jar /opt/chat-graph.jar
CMD ["mkdir", "-p", "/opt/src/main/resources"]
COPY graph_exec.json /opt/src/main/resources/
CMD ["java", "-jar", "/opt/chat-graph.jar"]