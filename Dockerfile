#
# Build Stage
#   
FROM maven:4.0.0-alpha-2 AS Build
COPY . .
RUN mvn clean package -DskipTests
        
#
# Package stage
#
FROM openjdk:19
COPY --from=build /target/basictodo-0.0.1-SNAPSHOT.jar basictodo-0.0.1-SNAPSHOT.jar

# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "basictodo-0.0.1-SNAPSHOT.jar"]