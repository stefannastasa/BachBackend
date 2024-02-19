plugins {
    id("java")
    id ("org.springframework.boot") version "3.2.1"
    id ("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("idea")

}
group = "org.handnotes"
version = "1.0-SNAPSHOT"

idea {
    module{
        isDownloadSources = true
    }
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-websocket:3.2.2")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.470")


    // jjwt
    implementation ("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.12.3")
}

tasks.test {
    useJUnitPlatform()
}