FROM openjdk:8-jre-alpine

COPY target/*.jar /srv/app.jar
WORKDIR /srv/

ENV JAVA_OPTS="-Xmx256m -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_PROFILES_ACTIVE=h2,default

EXPOSE 8080
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]