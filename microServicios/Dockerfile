FROM openjdk:17-jdk-alpine

WORKDIR /app/microServicios

COPY ./pom.xml /app
COPY ./microServicios/.mvn  ./.mvn
COPY ./microServicios/mvnw .
COPY ./microServicios/pom.xml .




#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && -r ./target/

RUN ./mvnw dependency:go-offline

COPY ./microServicios/src ./src

RUN ./mvnw clean package -DskipTests
ENV PORT 8001
EXPOSE $PORT

CMD ["java", "-jar", "./target/microServicios-0.0.1-SNAPSHOT.jar"]
