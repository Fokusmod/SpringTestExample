FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:8.5.47-jdk8-openjdk
COPY --from=build /app/target/SpringTest.war /usr/local/tomcat/webapps/Test-job.war
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
