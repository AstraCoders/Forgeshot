import java.io.FileInputStream
import java.util.Properties
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar

plugins {
    `java-library`
    `maven-publish`
}
val mod_name: String by project
val mod_author: String by project
val minecraft_version: String by project
val java_version: String by project
val minecraft_version_range: String by project
val mod_id: String by project
val license: String by project
val neoforge_version: String by project
val neoforge_loader_version_range: String by project
val credits: String by project
val forge_version: String by project
val forge_loader_version_range: String by project

fun loadSecrets(): Properties {
    val props = Properties()
    val secretsFile = file("../secrets.properties")
    if (secretsFile.exists()) {
        FileInputStream(secretsFile).use { props.load(it) }
    }
    return props
}

val secrets = loadSecrets()
val curseforgeKey = loadCurseforgeKey()
val modrinthKey = loadModrinthKey()
val changelog = loadChangelogPath()

fun loadCurseforgeKey(): String {
    return when {
        secrets.getProperty("curseforge_key") != null -> secrets.getProperty("curseforge_key")
        System.getenv("CURSEFORGE_KEY_SECRET") != null -> System.getenv("CURSEFORGE_KEY_SECRET")
        else -> "DUMMY"
    }
}

fun loadModrinthKey(): String {
    return when {
        secrets.getProperty("modrinth_key") != null -> secrets.getProperty("modrinth_key")
        System.getenv("MODRINTH_KEY_SECRET") != null -> System.getenv("MODRINTH_KEY_SECRET")
        else -> "DUMMY"
    }
}

fun loadChangelogPath(): String = "CHANGELOG.md"

extra.apply {
    set("secrets", secrets)
    set("curseforgeKey", curseforgeKey)
    set("modrinthKey", modrinthKey)
    set("changelog", changelog)
}

rootProject.extra.apply {
    set("secrets", secrets)
    set("curseforgeKey", curseforgeKey)
    set("modrinthKey", modrinthKey)
    set("changelog", changelog)
}

base {
    archivesName.set("${property("mod_id")}-mc${property("minecraft_version")}+${project.version}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(property("java_version").toString().toInt()))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()

    exclusiveContent {
        forRepository {
            maven {
                name = "Sponge"
                url = uri("https://repo.spongepowered.org/repository/maven-public")
            }
        }
        filter {
            includeGroupAndSubgroups("org.spongepowered")
        }
    }

    maven {
        name = "BlameJared"
        url = uri("https://maven.blamejared.com")
    }
}

tasks.named<Jar>("sourcesJar") {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${mod_name}" }
    }
}

tasks.named<Jar>("jar") {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${mod_name}" }
    }

    manifest {
        attributes(
                mapOf(
					"Specification-Title" to mod_name,
					"Specification-Vendor" to mod_author,
					"Specification-Version" to archiveVersion.get(),
					"Implementation-Title" to project.name,
					"Implementation-Version" to archiveVersion.get(),
					"Implementation-Vendor" to mod_author,
					"Built-On-Minecraft" to minecraft_version,
                )
        )
    }
}

tasks.processResources {
    val expandProps = mapOf(
		"version" to version,
		"group" to project.group,
		"minecraft_version" to minecraft_version,
		"minecraft_version_range" to minecraft_version_range,
		"mod_name" to mod_name,
		"mod_author" to mod_author,
		"mod_id" to mod_id,
		"license" to license,
		"description" to (project.description ?: ""),
		"forge_version" to forge_version,
		"forge_loader_version_range" to forge_loader_version_range,
		"neoforge_version" to neoforge_version,
		"neoforge_loader_version_range" to neoforge_loader_version_range,
		"credits" to credits,
		"java_version" to java_version,
    )

    val jsonExpandProps = expandProps.mapValues { (_, value) ->
        if (value is String) value.replace("\n", "\\n") else value
    }

    filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
        expand(expandProps)
    }

    filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "*.mixins.json")) {
        expand(jsonExpandProps)
    }

    inputs.properties(expandProps)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }

    repositories {
        System.getenv("local_maven_url")?.let { localMavenUrl ->
            maven {
                url = uri(localMavenUrl)
            }
        }
    }
}
