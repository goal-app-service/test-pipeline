pipeline {
    agent any
    environment {
        BUILD_ID = ${env.BUILD_ID}
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
        stage('Publish') {
            environment {
                registryCredential = 'dockerhub'
            }
            steps{
                dir('build/docker'){
                    script{
                        def appimage = docker.build("pokl/test-pipeline:${BUILD_ID}", "-f Dockerfile .")
                        docker.withRegistry( '', registryCredential ) {
                            appimage.push()
                            appimage.push('latest')
                        }
                    }
                }
            }
        }
    }
}