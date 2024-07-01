FROM openjdk:17
LABEL authors="ValerionVadim"
EXPOSE 8081
WORKDIR /opt/app
VOLUME /data
ENV JAVA_OPTIONS="-Xms256m -Xmx1024m"
COPY target/*.war Task_Scheduler-1.0.war
CMD java $JAVA_OPTIONS -jar Task_Scheduler-1.0.war
