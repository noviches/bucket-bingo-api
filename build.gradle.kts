import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.9.20"

	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"

	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
	kotlin("kapt") version kotlinVersion

	id("org.openapi.generator") version "6.6.0"
}

group = "com.bucketbingo"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("org.hashids:hashids:1.0.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = JavaVersion.VERSION_17.toString()
	}
}

tasks.withType<JavaCompile> {
	sourceCompatibility = JavaVersion.VERSION_17.toString()
	targetCompatibility = JavaVersion.VERSION_17.toString()
}

tasks.withType<Test> {
	useJUnitPlatform()
}

openApiGenerate {
	generatorName.set("kotlin-spring")
	generateApiDocumentation.set(false)
	generateModelDocumentation.set(false)

	inputSpec.set("$rootDir/src/main/resources/bucket-bingo-api.yaml")
	outputDir.set("$rootDir")

	apiPackage.set("com.bucketbingo.api.adapter.in.rest.operations")
	modelPackage.set("com.bucketbingo.api.adapter.in.rest.models")

	configOptions.set(
		mapOf(
			"reactive" to "false",
			"interfaceOnly" to "true",
			"enumPropertyNaming" to "UPPERCASE",
			"useBeanValidation" to "true",
			"useTags" to "true",
			"annotationLibrary" to "none",
			"documentationProvider" to "none",
			"useSpringBoot3" to "true"
		)
	)

	additionalProperties = mapOf(
		"gradleBuildFile" to "false"
	)
}