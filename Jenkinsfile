pipeline {
    agent any
    environment {
        BUILD_ID = "${env.BUILD_ID}"
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Copy Jar') {
            steps {
                sh './gradlew copyJar'
             }
        }
        stage('Create Dockerfile') {
            steps {
                sh './gradlew createDockerfile'
            }
        }
    }
}
