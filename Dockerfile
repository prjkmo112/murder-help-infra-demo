FROM --platform=$BUILDPLATFORM eclipse-temurin:17-jdk AS builder
WORKDIR /workspace

COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src

RUN chmod +x gradlew  && ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:17-jre
WORKDIR /app

RUN useradd --create-home --shell /bin/bash app

COPY --from=builder /workspace/build/libs/*.jar app.jar

USER app

EXPOSE 8080

# 배포 환경(EC2 등)에서는 Parameter Store 값을 env-file 로 주입만 하면 되도록
# prod 프로파일을 기본값으로 고정한다. 필요 시 컨테이너 실행 시 -e SPRING_PROFILES_ACTIVE=... 로 덮어쓸 수 있다.
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]