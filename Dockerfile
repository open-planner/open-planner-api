FROM openjdk:13-jdk-alpine
LABEL maintainer="Pedro Pacheco <pedrovcpacheco@yahoo.com>"
RUN apk add git && \    
    git clone https://gitlab.com/ifpb-es/open-planner/open-planner-backend.git
WORKDIR open-planner-backend
#RUN   
#ENTRYPOINT ["java"]
#CMD [""]
EXPOSE 8080
