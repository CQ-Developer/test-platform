plugins {
	java
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "org.huhu"
version = "0.0.1"
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
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-security")
	runtimeOnly("org.springframework.data:spring-data-r2dbc")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("io.r2dbc:r2dbc-h2")
	runtimeOnly("io.r2dbc:r2dbc-pool")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("io.projectreactor:reactor-test")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
