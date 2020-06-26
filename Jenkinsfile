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
                        sh '/usr/local/bin/docker build -t pokl/test:latest .'
                        sh 'docker image tag test:latest pokl/test:latest'
                    }
                }
            }
        }
        stage('Publish') {
            steps {
                dir('build/docker') {
                    script{
                        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                            sh "/usr/local/bin/docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                            sh '/usr/local/bin/docker push pokl/test:latest'
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    sh '/usr/local/bin/kubectl apply -f k8s/'
                }
            }
        }
    }
}