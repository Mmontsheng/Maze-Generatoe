FROM amazoncorretto:11
ENV DISPLAY :10
WORKDIR /application
COPY ./target/MazeGenerator-jar-with-dependencies.jar application.jar
#RUN java -jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
