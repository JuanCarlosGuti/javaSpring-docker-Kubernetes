FROM openjdk:17-jdk-alpine

WORKDIR /app/cursos

COPY ./pom.xml /app
COPY ./cursos/.mvn  ./.mvn
COPY ./cursos/mvnw .
COPY ./cursos/pom.xml .


#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && -r ./target/

RUN ./mvnw dependency:go-offline

COPY ./cursos/src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8002

CMD ["java", "-jar", "./target/cursos-0.0.1-SNAPSHOT.jar"]

