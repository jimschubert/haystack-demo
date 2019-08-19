import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.1.7.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version "1.2.71"
	kotlin("plugin.jpa") version "1.2.71"
	kotlin("plugin.spring") version "1.2.71"
}

group = "us.jimschubert"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven("https://dl.bintray.com/kotlin/exposed")
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	implementation("org.jetbrains.exposed:exposed:0.17.1")
	implementation("org.jetbrains.exposed:spring-transaction:0.17.1")
	implementation("com.h2database:h2:1.4.199")

	implementation("com.squareup.okhttp3:okhttp:3.10.0")
	implementation("io.opentracing.contrib:opentracing-okhttp3:2.0.1")

	implementation("io.micrometer:micrometer-registry-jmx")
	implementation("io.opentracing.contrib:opentracing-jdbc:0.0.12")
	implementation("com.expedia.www:opentracing-spring-haystack-starter:0.2.7")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<Jar> {
	archiveName = "haystack-demo.jar"
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
		suppressWarnings = true
	}
}
