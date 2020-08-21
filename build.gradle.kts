/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.4.0"
}

repositories {
    // mavenLocal()
//     mavenCentral()
//    maven {
//        url = uri("http://repo1.maven.org/maven2")
//    }
//
//    maven {
//        url = uri("http://repo.maven.apache.org/maven2")
//    }
    mavenCentral()
}

dependencies {
    implementation("org.apache.maven.plugins:maven-resources-plugin:3.0.2")
    implementation ("org.apache.maven.plugins:maven-compiler-plugin:3.7.0")
    implementation ("org.apache.maven.plugins:maven-clean-plugin:3.0.0")
    testImplementation ("junit:junit:4.12")
    testImplementation ("org.hamcrest:hamcrest-all:1.3")
}

group = "cz.startnet"
version = "2.6.10-SNAPSHOT"
description = "Another PostgreSQL Diff Tool"
// sourceCompatibility = "1.8"

java {
    withSourcesJar()
    withJavadocJar()
}

//publishing {
//    publications {
//        maven(MavenPublication) {
//            from(components.java)
//        }
//    }
//}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

// compile bytecode to java 8 (default is java 6)
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
