import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenCentral()
}
plugins {
    val kotlinVersion = "1.7.22"
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    idea
}
val asciidoctorExt by configurations.creating
val snippetsDir by extra { file("build/generated-snippets") }
group = "com.example"
version = "timedeal-server-1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17


extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("com.github.f4b6a3:ulid-creator:5.1.0")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.mariadb.jdbc:mariadb-java-client")
    //querydsl

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    //test
    runtimeOnly("com.h2database:h2")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test"){exclude(module="mockito-core")}
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor:3.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
    annotation("org.springframework.stereotype.Component")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
    annotation("org.springframework.stereotype.Component")
}
tasks.bootJar{
    archiveBaseName.set("com.example")
    archiveFileName.set("myapp.jar")
}
idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}
tasks.test{

    useJUnitPlatform()
    testLogging {
        setExceptionFormat("full")
        setEvents(listOf("started", "skipped", "passed", "failed"))
        showStandardStreams=true
    }
    outputs.dir(snippetsDir)

}
tasks.asciidoctor{
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
    configurations(asciidoctorExt.name)
    sources{include("**/index.adoc") }
    baseDirFollowsSourceFile()
}

tasks.register<Copy>("copyDocument"){
    doFirst{
        delete(file("src/main/resources/static/docs"))
    }
    from("build/docs/asciidoc")
    into("src/main/resources/static/docs")
    dependsOn(tasks.asciidoctor)
}
tasks.build{
    dependsOn("copyDocument")
}
tasks.bootJar{

    dependsOn("copyDocument")
    from("${tasks.asciidoctor.get().outputDir}") {
        into("BOOT-INF/classes/static/docs")
    }
    duplicatesStrategy  = DuplicatesStrategy.EXCLUDE
}