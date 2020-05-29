FROM openjdk:11

ENV _JAVA_OPTIONS="-Xmx512m -Xms256m"

ADD target/open-planner-api.jar /opt/open-planner-api.jar
ENTRYPOINT ["java", "-Duser.timezone=GMT-03:00", "-jar", "/opt/open-planner-api.jar"]

EXPOSE 8080