import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    `maven-publish`
    signing
}

android {
    compileSdk = 35
    namespace = "com.google.android.renderscrip"
    defaultConfig {
        minSdk = 21

        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
//                cppFlags("-std=c++17")
                arguments += listOf("-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON")
            }
        }
    }
    ndkVersion = "29.0.13846066"

    buildTypes {
        getByName("release") {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "4.1.0"
        }
    }
    publishing {
        singleVariant("release") {
            // if you don't want sources/javadoc, remove these lines
            withSourcesJar()
//            withJavadocJar()
        }
    }
}

dependencies {

}



//---------- maven upload info -----------------------------------

val versionName = "1.0.1"

var signingKeyId = ""//签名的密钥后8位
var signingPassword = ""//签名设置的密码
var secretKeyRingFile = ""//生成的secring.gpg文件目录


val localProperties: File = project.rootProject.file("local.properties")

if (localProperties.exists()) {
    println("Found secret props file, loading props")
    val properties = Properties()

    InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
        properties.load(reader)
    }
    signingKeyId = properties.getProperty("signing.keyId")
    signingPassword = properties.getProperty("signing.password")
    secretKeyRingFile = properties.getProperty("signing.secretKeyRingFile")

} else {
    println("No props file, loading env vars")
}

afterEvaluate {

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.findByName("release"))
                groupId = "io.github.limuyang2"
                artifactId = "renderscrip-toolkit"
                version = versionName

                pom {
                    name.value("enderscrip-toolkit")
                    description.value("Android RenderScript-toolkit.")
                    url.value("https://github.com/limuyang2/RenderScript-toolkit")

                    licenses {
                        license {
                            //协议类型
                            name.value("The MIT License")
                            url.value("https://github.com/limuyang2/RenderScript-toolkit/blob/main/LICENSE")
                        }
                    }

                    developers {
                        developer {
                            id.value("limuyang2")
                            name.value("limuyang")
                            email.value("limuyang2@hotmail.com")
                        }
                    }

                    scm {
                        connection.value("scm:git@github.com:limuyang2/RenderScript-toolkit.git")
                        developerConnection.value("scm:git@github.com:limuyang2/RenderScript-toolkit.git")
                        url.value("https://github.com/limuyang2/RenderScript-toolkit")
                    }
                }
            }

        }

        repositories {
            maven {
                setUrl("$rootDir/RepoDir")
            }
        }



    }

}

gradle.taskGraph.whenReady {
    if (allTasks.any { it is Sign }) {

        allprojects {
            extra["signing.keyId"] = signingKeyId
            extra["signing.secretKeyRingFile"] = secretKeyRingFile
            extra["signing.password"] = signingPassword
        }
    }
}

signing {
    sign(publishing.publications)
}