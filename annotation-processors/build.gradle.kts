import org.jetbrains.kotlin.ir.backend.js.compile

/*
 * Copyright 2003-2023 The IdeaVim authors
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE.txt file or at
 * https://opensource.org/licenses/MIT.
 */

plugins {
  kotlin("jvm")
}

group = "com.intellij"
version = "SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  compileOnly("com.google.devtools.ksp:symbol-processing-api:1.8.21-1.0.11")
  implementation("org.yaml:snakeyaml:2.0")
}