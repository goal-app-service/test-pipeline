rootProject.name = "test-pipeline"

pluginManagement {
    val dockerRemoteApiVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.bmuschko.docker-remote-api" -> useVersion(dockerRemoteApiVersion)
            }
        }
    }
}