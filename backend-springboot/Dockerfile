# Étape 1 : Build avec Maven
FROM maven:3.9-eclipse-temurin-21 AS build

# Répertoire de travail pour le build
WORKDIR /workspace

# Copier les fichiers du projet
COPY . .

# Construire l'application
RUN mvn clean install -DskipTests

# Étape 2 : Image d’exécution minimale
FROM eclipse-temurin:21-jdk-jammy

# Répertoire de travail
WORKDIR /app

# Copie du jar
COPY --from=build /workspace/target/*.jar app.jar

# Port exposé
EXPOSE 3003

# Variables d’environnement (optionnel)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80.0"

# Entrypoint clair, compatible avec JAVA_OPTS
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
