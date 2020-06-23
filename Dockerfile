FROM openjdk:11
LABEL maintainer="Pedro Pacheco <pedrovcpacheco@yahoo.com>"
ENV PATH=/open-planner-backend/apache-maven-3.6.3/bin:$PATH
RUN git clone https://gitlab.com/ifpb-es/open-planner/open-planner-backend.git
WORKDIR open-planner-backend
RUN  wget http://ftp.unicamp.br/pub/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz && \
         tar xf apache-maven-3.6.3-bin.tar.gz && \
         mvn clean install -DskipTests
ENTRYPOINT ["java", "-Duser.timezone=GMT-03:00", "-jar", "target/*.jar"]
EXPOSE 8080
