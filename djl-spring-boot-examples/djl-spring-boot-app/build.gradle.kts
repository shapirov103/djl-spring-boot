import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("com.google.osdetector") version "1.6.2"
	id("org.springframework.boot") version "2.2.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("java")
  kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
}

group = "com.aws.samples"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_13

repositories {
	mavenCentral()
	maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("commons-cli:commons-cli:1.4")
	implementation("com.google.code.gson:gson:2.8.5")
	implementation("ai.djl:api:0.3.0-SNAPSHOT")
	implementation("ai.djl:basicdataset:0.3.0-SNAPSHOT")
	implementation("ai.djl:model-zoo:0.3.0-SNAPSHOT")
	implementation("ai.djl.mxnet:mxnet-model-zoo:0.3.0-SNAPSHOT")
	implementation("net.java.dev.jna:jna:5.3.0") // todo replace
  implementation("com.amazonaws:aws-java-sdk-s3:1.11.714")

	// See: https://github.com/awslabs/djl/blob/master/mxnet/mxnet-engine/README.md for MXNet library selection
	runtimeOnly("ai.djl.mxnet:mxnet-native-mkl:1.6.0-c-SNAPSHOT:${osdetector.classifier}")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "12"
	}
}
