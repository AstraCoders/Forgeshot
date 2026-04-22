import java.io.ByteArrayOutputStream

extra["mod_changelog"] =
        "No changelog was provided. Please refer to the project page for more information."

fun getExecOutput(commands: List<String>): String {
    val process = ProcessBuilder(commands)
            .redirectErrorStream(true)
            .start()

    val out = ByteArrayOutputStream()
    process.inputStream.use { it.copyTo(out) }

    val exitCode = process.waitFor()
    if (exitCode != 0) {
        throw GradleException("Command failed with exit code $exitCode: ${commands.joinToString(" ")}")
    }

    return out.toString().trim()
}

tasks.register("genChangelog") {
    doLast {
        try {
            project.extra["mod_changelog"] =
                    "No changelog was provided. Please refer to the project page for more information."

            val gitCommit = System.getenv("GIT_COMMIT")
                    ?: getExecOutput(listOf("git", "log", "-n", "1", "--pretty=tformat:%h"))

            val gitPrevCommit = System.getenv("GIT_PREVIOUS_COMMIT")

            // If a full range is available use that range.
            if (!gitCommit.isNullOrBlank() && !gitPrevCommit.isNullOrBlank()) {
                project.extra["mod_changelog"] = getExecOutput(
                        listOf("git", "log", "--pretty=tformat:- %s", "$gitPrevCommit..$gitCommit")
                )
                project.logger.lifecycle(
                        "Generated changelog using commits $gitPrevCommit to $gitCommit."
                )
            }
            // If only one commit is available, use the last commit.
            else if (!gitCommit.isNullOrBlank()) {
                project.extra["mod_changelog"] = getExecOutput(
                        listOf("git", "log", "--pretty=tformat:- %s", "-1", gitCommit)
                )
                project.logger.lifecycle("Generated changelog using commit $gitCommit.")
            }

            rootDir.toPath()
                    .resolve("CHANGELOG.md")
                    .toFile()
                    .writeText("## Changelog\n\n${project.extra["mod_changelog"]}")

            // rootDir.toPath().resolve("LAST_COMMIT").toFile().writeText(gitCommit ?: "")
            // file("CHANGELOG.md").writeText(project.extra["mod_changelog"].toString())
        } catch (e: Exception) {
            project.logger.warn("Changelogs could not be generated! ${e.message}")
        }
    }
}

rootProject.extra["mod_changelog"] =
        rootDir.toPath().resolve("CHANGELOG.md").toFile().readText()
