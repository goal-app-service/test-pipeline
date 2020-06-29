pipeline {
    agent any
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
                        def appimage = docker.build("test:latest", "-f Dockerfile .")
                        sh 'image tag test:latest pokl/test:latest'
                        docker.withRegistry( '', registryCredential ) {
                            appimage.push()
                            appimage.push('latest')
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    sh 'kubectl delete -f k8s/'
                    sh 'kubectl apply -f k8s/'
                }
            }
        }
    }
}