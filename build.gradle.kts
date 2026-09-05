import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test

plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

sourceSets {
    main {
        java {
            srcDirs("common/java")
        }
    }
    test {
        java {
            srcDirs("problems", "tests/java")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
    options.encoding = "UTF-8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("file.encoding", "UTF-8")
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.test {
    testLogging {
        events("failed") // 실패한 테스트 이벤트 감지
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL // 전체 스택 트레이스 출력
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}
