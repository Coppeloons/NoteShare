FROM eclipse-temurin:20-jre-jammy
MAINTAINER coppeloons.org
COPY target/NoteShare-0.0.1-SNAPSHOT.jar 0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","0.0.1-SNAPSHOT.jar"]
