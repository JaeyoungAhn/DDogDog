FROM openjdk:17

# Define build argument
ARG JAR_FILE=build/libs/DDogDog-0.0.1-SNAPSHOT.jar

# Create a working directory
WORKDIR /app

# Copy the jar file into the working directory
COPY ${JAR_FILE} /app

RUN mkdir -p /app/logs /app/core && \
    chmod -R 777 /app

# Set timezone environment variable
ENV TZ=Asia/Seoul

# Expose the port the application runs on
EXPOSE 8080

# Define the entry point to run the jar file
ENTRYPOINT ["java", "-jar", "DDogDog-0.0.1-SNAPSHOT.jar"]