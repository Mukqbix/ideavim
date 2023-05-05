/*
 * Copyright 2003-2023 The IdeaVim authors
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE.txt file or at
 * https://opensource.org/licenses/MIT.
 */

plugins {
    java
    kotlin("jvm")
//    id("org.jlleitschuh.gradle.ktlint")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
}

// group 'org.jetbrains.ideavim'
// version 'SNAPSHOT'

repositories {
    mavenCentral()
}

ksp {
  arg("generated_directory", "$projectDir/src/main/resources")
  arg("vimscript_functions_file", "engine_vimscript_functions.yaml")
  arg("ex_commands_file", "engine_ex_commands.yaml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")

    compileOnly("org.jetbrains:annotations:24.0.1")

    ksp(project(":annotation-processors"))
    implementation(project(":annotation-processors"))
    implementation("org.yaml:snakeyaml:2.0")
}

tasks {
    val test by getting(Test::class) {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions {
            apiVersion = "1.5"
            freeCompilerArgs = listOf("-Xjvm-default=all-compatibility")
        }
    }
}

// --- Linting

//ktlint {
//    version.set("0.48.2")
//}

kotlin {
    explicitApi()
}
