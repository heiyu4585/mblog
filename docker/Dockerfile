FROM openjdk:17
VOLUME /tmp
ADD mBlog-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=pro"]