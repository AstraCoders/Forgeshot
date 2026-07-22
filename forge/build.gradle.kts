import org.gradle.api.attributes.Attribute
import org.gradle.jvm.tasks.Jar

plugins {
    id("idea")
    id("multiloader-loader")
    id("net.minecraftforge.gradle") version "[7.0.23,8.0)"
    id("net.minecraftforge.jarjar") version "0.2.3"

    id("net.darkhax.curseforgegradle")
    id("com.modrinth.minotaur")
}

base {
    archivesName.set("${property("mod_name")}-forge-${property("minecraft_version")}")
}

val mixinConfigs = listOf(
    "${property("mod_id")}.mixins.json",
    "${property("mod_id")}.forge.mixins.json"
)

// Include generated resources
sourceSets.named("main") {
    resources.srcDir(project(":common").file("src/generated/resources/").absolutePath)
}

minecraft {
    // Forge still uses SRG names during compile time, so we cannot use the common AT's
    val at = file("src/main/resources/META-INF/accesstransformer.cfg")
//    if (at.exists()) {
//        accessTransformer = at
//    }

    runs {
        configureEach {
            systemProperty("eventbus.api.strictRuntimeChecks", "true")
            systemProperty("forge.enabledGameTestNamespaces", property("mod_id").toString())
            systemProperty("mixin.debug.verbose", "true")
            systemProperty("mixin.debug.export", "true")

            mixinConfigs.forEach { config ->
                args("--mixin.config=$config")
            }
        }

        register("client") {
            workingDir.convention(layout.projectDirectory.dir("runs/client"))
            mods {
                create("modClientRun") {
                    source(sourceSets.main.get())
                }
            }
        }

        register("server") {
            workingDir.convention(layout.projectDirectory.dir("runs/server"))
            mods {
                create("modServerRun") {
                    source(sourceSets.main.get())
                }
            }
        }

        register("data") {
            workingDir.convention(layout.projectDirectory.dir("runs/data"))
            args(
                "--mod", property("mod_id").toString(),
                "--all",
                "--output", layout.projectDirectory.dir("src/generated/resources/").asFile.absolutePath,
                "--existing", layout.projectDirectory.dir("src/main/resources/").asFile.absolutePath
            )
            mods {
                create("modClientRun") {
                    source(sourceSets.main.get())
                }
            }
        }
    }
}

repositories {
    minecraft.mavenizer(this)
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
}

sourceSets.main {
    resources.srcDir("src/generated/resources")
}

sourceSets.configureEach {
    val outputDir = layout.buildDirectory.dir("sourceSets/$name")
    output.setResourcesDir(outputDir)
    java.destinationDirectory.set(outputDir)
}

dependencies {
    implementation(minecraft.dependency("net.minecraftforge:forge:${property("minecraft_version")}-${property("forge_version")}"))
    annotationProcessor("org.spongepowered:mixin:0.8.7:processor")
    annotationProcessor("net.minecraftforge:eventbus-validator:7.0.1")
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            mapOf(
                "MixinConfigs" to mixinConfigs.joinToString(","),
            )
        )
    }
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)

listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations.findByName(variant)?.attributes {
        attribute(loaderAttribute, "forge")
    }
}

sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant ->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "forge")
            }
        }
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks.register<net.darkhax.curseforgegradle.TaskPublishCurseForge>("publishCurseForge") {
    dependsOn(tasks.jar)
    group = "publishing"
    apiToken = rootProject.extra["curseforgeKey"].toString()

    val mainFile = upload(project.property("curseforge_project_id").toString(), tasks.jar.get())
    mainFile.releaseType = "release"
    mainFile.changelogType = "text"
    mainFile.changelog = rootProject.extra["mod_changelog"].toString()
    // mainFile.addJavaVersion("Java ${project.property("java_version")}")
    mainFile.addGameVersion(project.property("minecraft_version").toString())
    mainFile.addModLoader("Forge")

    // mainFile.addRequirement("")
}

modrinth {
    token.set(rootProject.extra["modrinthKey"].toString())
    projectId.set(project.property("modrinth_project_id").toString())
    versionNumber.set("${project.property("minecraft_version")}-${project.version}")
    versionName.set("${project.version} for Forge ${project.property("minecraft_version")}")
    versionType.set("release")
    uploadFile.set(tasks.jar)
    gameVersions.set(listOf(project.property("minecraft_version").toString()))
    changelog.set(provider { rootProject.extra["mod_changelog"].toString() })
    loaders.set(listOf("forge"))
}
