import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
}

group = "com.hustle-knight"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework:spring-webflux:5.3.10")

    implementation("org.springframework.boot:spring-boot-starter-validation")

    //redis
    implementation ("org.springframework.boot:spring-boot-starter-data-redis")
    //RabbitMQ
    implementation ("org.springframework.boot:spring-boot-starter-amqp")

    //SWAGGER
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    //JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    //Logger
    implementation("org.springframework.boot:spring-boot-starter-logging")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //H2
    runtimeOnly("com.h2database:h2")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    //testContainer
    testImplementation("org.springframework.boot:spring-boot-testcontainers")  // TC 의존성
    testImplementation("org.testcontainers:mysql:1.19.3")  // MySQL 테스트 컨테이너 사용
    testImplementation("org.testcontainers:junit-jupiter:1.16.3")  // TC 의존성
    testImplementation("com.mysql:mysql-connector-j:8.2.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
