# Usa a imagem oficial do OpenJDK
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e baixa dependências
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copia o restante do código para dentro do container
COPY . .

# Compila o projeto e cria o .jar
RUN ./mvnw clean package -DskipTests

# Expõe a porta que o Spring Boot usa
EXPOSE 8080

# Comando para rodar o app
ENTRYPOINT ["java", "-jar", "target/ServiFacil-BackEnd-0.0.1-SNAPSHOT.jar"]