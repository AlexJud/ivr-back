FROM openjdk:8
COPY chat-graph*.jar /opt/
CMD ["java", "-jar", "/opt/chat-graph.jar"]