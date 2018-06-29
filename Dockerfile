FROM images/alpine-java
EXPOSE 9900
COPY ./target/dispatcher-service.jar /sxdata/dispatcher-service/
WORKDIR /sxdata/dispatcher-service/
CMD ["java", "-jar", "dispatcher-service.jar"]