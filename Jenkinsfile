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
                            sh 'docker login -u ${dockerHubUser} -p ${dockerHubPassword}'
                            sh 'docker push pokl/test:latest'
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    withKubeConfig([credentialsId: 'service-account', serverUrl:"${KUBERNETES_URL}"]){
                        sh 'kubectl delete -f k8s/'
                        sh 'kubectl apply -f k8s/'
                    }
                }
            }
        }
        stage('Slack it'){
            steps {
                slackSend channel: '#goal-app',
                          message: 'Pipeline is finished'
                }
            }
        }
    }
}