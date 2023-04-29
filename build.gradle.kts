plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "org.huhu"
version = "0.0.2"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://maven.aliyun.com/repository/spring/")
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("cn.hutool:hutool-all:5.8.18")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.r2dbc:r2dbc-h2")
    runtimeOnly("com.github.jasync-sql:jasync-r2dbc-mysql:2.1.24")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<Test> {
    maxParallelForks = 6
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<JacocoReport> {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
    }
}
