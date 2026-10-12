import org.gradle.api.attributes.Attribute
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.language.jvm.tasks.ProcessResources
plugins {
    id("multiloader-common")
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
val commonProject = project(":common:${property("minecraft_version")}")
val loaderSources = layout.buildDirectory.dir("generated/stonecutter/main")
val commonSources = commonProject.layout.buildDirectory.dir("generated/stonecutter/main")
val commonVersionResources = commonProject.layout.projectDirectory.dir("src/main/resources")
val activeIdeaSync = System.getProperty("idea.sync.active") == "true" &&
    property("minecraft_version").toString() == rootProject.property("minecraft_version").toString()

sourceSets.main {
    java.setSrcDirs(
        if (activeIdeaSync) listOf(
            projectDir.resolve("src/main/java"),
            projectDir.parentFile.parentFile.resolve("src/main/java"),
            commonProject.projectDir.resolve("src/main/java"),
            commonProject.projectDir.parentFile.parentFile.resolve("src/main/java"),
        ) else listOf(loaderSources.map { it.dir("java") }, commonSources.map { it.dir("java") })
    )
    resources.setSrcDirs(listOf(loaderSources.map { it.dir("resources") }, commonSources.map { it.dir("resources") }, commonVersionResources))
}

tasks.named<JavaCompile>("compileJava") {
    setSource(files(loaderSources.map { it.dir("java") }, commonSources.map { it.dir("java") }))
    dependsOn(tasks.named("stonecutterGenerate"))
    dependsOn(commonProject.tasks.named("stonecutterGenerate"))
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(tasks.named("stonecutterGenerate"))
    dependsOn(commonProject.tasks.named("stonecutterGenerate"))
}
