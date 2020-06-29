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
        stage('Docker Build') {
            steps {
                dir('build/docker') {
                    script{
                        sh 'docker build -t test .'
                        sh 'image tag test:latest pokl/test:latest'
                    }
                }
            }
        }
        stage('Publish') {
            steps {
                dir('build/docker') {
                    script{
                        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                            sh 'docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}'
                            sh 'docker push pokl/test:latest'
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