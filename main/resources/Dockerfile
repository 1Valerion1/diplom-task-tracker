FROM openjdk:17
EXPOSE 8081
WORKDIR /opt/app
VOLUME /data
ENV JAVA_OPTIONS="-Xms256m -Xmx512m"
COPY target/*.jar app.jar
CMD java $JAVA_OPTIONS -jar app.jar