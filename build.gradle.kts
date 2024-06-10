plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.teamsparta"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Data JPA - 데이터베이스와 상호작용하기 위한 스프링 데이터 JPA 스타터
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Spring Web - 웹 애플리케이션을 개발하기 위한 스프링 웹 스타터
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Jackson Kotlin module - JSON 직렬화/역직렬화를 위한 Jackson 모듈
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Kotlin Reflect - Kotlin 리플렉션을 사용하기 위한 라이브러리
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Kotlin Standard Library - Kotlin 표준 라이브러리
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // PostgreSQL Driver - PostgreSQL 데이터베이스에 연결하기 위한 드라이버
    implementation("org.postgresql:postgresql:42.7.3")

    // SpringDoc OpenAPI - Spring MVC와 통합된 OpenAPI 문서화 도구
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // Spring Boot DevTools - 개발 편의를 위한 Spring Boot DevTools (Hot reload 등)
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // H2 Database - 테스트용 인메모리 데이터베이스 (runtimeOnly로 실제 애플리케이션 실행 시 사용되지 않음)
    runtimeOnly("com.h2database:h2")

    // Spring Boot Test Starter - 테스트를 위한 스프링 부트 스타터
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // JUnit Platform Launcher - JUnit 플랫폼 런처 (테스트 실행용)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
