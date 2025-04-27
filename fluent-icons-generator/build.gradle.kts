plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.java)
                implementation(libs.jsoup)
                implementation(libs.google.guava)
                implementation(libs.android.tools.common)
                implementation(libs.android.tools.sdk.common)
                implementation(libs.squareup.kotlinpoet)
                // https://mvnrepository.com/artifact/xmlpull/xmlpull
                implementation("xmlpull:xmlpull:1.1.3.1")
                // https://mvnrepository.com/artifact/org.apache.xml.serializer/serializer
                implementation("xerces:xercesImpl:2.8.1")
            }
        }
        val jvmTest by getting
    }
}