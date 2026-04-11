FROM maven:3.9.9-eclipse-temurin-21

# Install Chrome
RUN apt-get update && apt-get install -y wget unzip curl \
 && wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
 && apt-get install -y ./google-chrome-stable_current_amd64.deb

WORKDIR /app

# Copy project
COPY docker .

# Build project
RUN mvn clean install -DskipTests

# Add entrypoint
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]



