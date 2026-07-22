import org.gradle.api.attributes.Attribute

plugins {
    id("multiloader-loader")
    id("net.neoforged.moddev")
    id("net.darkhax.curseforgegradle")
    id("com.modrinth.minotaur")
}

base {
    archivesName.set("${property("mod_name")}-neoforge-${property("minecraft_version")}")
}

neoForge {
    version = property("neoforge_version").toString()

    // Automatically enable neoforge AccessTransformers if the file exists
    val at = project(":common").file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }

    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", property("mod_id").toString())
            ideName = "NeoForge ${name.replaceFirstChar { it.uppercase() }} (${project.path})"
        }
        create("client") {
            client()
            gameDirectory.set(project.file("runs/client"))
        }
        create("data") {
            clientData()
            gameDirectory.set(project.file("runs/data"))
            // DataGen can be run by - "./gradlew :neoforge:runData" in Terminal.
            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            programArguments.addAll(
                "--mod",
                property("mod_id").toString(),
                "--all",
                "--output",
                project.file("src/generated/resources/").absolutePath,
                "--existing",
                project.file("src/main/resources/").absolutePath,
            )
        }
        create("server") {
            server()
            gameDirectory.set(project.file("runs/server"))
        }
    }

    mods {
        create(property("mod_id").toString()) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.main {
    resources.srcDir("src/generated/resources")
}

// Implement mcgradleconventions loader attribute
val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)

listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations.named(variant) {
        attributes {
            attribute(loaderAttribute, "neoforge")
        }
    }
}

sourceSets.configureEach {
    listOf(
            compileClasspathConfigurationName,
            runtimeClasspathConfigurationName,
            getTaskName(null, "jarJar")
    ).forEach { variant ->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "neoforge")
            }
        }
    }
}

tasks.register<net.darkhax.curseforgegradle.TaskPublishCurseForge>("publishCurseForge") {
    dependsOn(tasks.jar)
    group = "publishing"
    apiToken = rootProject.extra["curseforgeKey"].toString()

    val mainFile = upload(rootProject.property("curseforge_project_id").toString(), tasks.jar.get())
    mainFile.releaseType = "release"
    mainFile.changelogType = "text"
    mainFile.changelog = rootProject.extra["mod_changelog"].toString()
    // mainFile.addJavaVersion("Java ${rootProject.property("java_version")}")
    mainFile.addGameVersion(rootProject.property("minecraft_version").toString())
    mainFile.addModLoader("NeoForge")
}

modrinth {
    token.set(rootProject.extra["modrinthKey"].toString())
    projectId.set(project.property("modrinth_project_id").toString())
    versionNumber.set("${project.property("minecraft_version")}-${project.version}")
    versionName.set("${project.version} for NeoForge ${project.property("minecraft_version")}")
    versionType.set("release")
    uploadFile.set(tasks.jar)
    gameVersions.set(listOf(project.property("minecraft_version").toString()))
    changelog.set(provider { rootProject.extra["mod_changelog"].toString() })
    loaders.set(listOf("neoforge"))
}
