plugins {
    // see https://fabricmc.net/develop/ for new versions
    id("net.fabricmc.fabric-loom") version "1.15.5" apply false
    // see https://projects.neoforged.net/neoforged/moddevgradle for new versions
    id("net.neoforged.moddev") version "2.0.141" apply false

    id("net.darkhax.curseforgegradle") version "1.+" apply false
    id("com.modrinth.minotaur") version "2.8.7" apply false
    id("git-changelog")
}

println("Changelog: ${project.extra["mod_changelog"]}")
