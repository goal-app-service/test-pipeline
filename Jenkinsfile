def COLOR_MAP = [
    'SUCCESS': 'good',
    'FAILURE': 'danger',
]

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
                        try {
                            sh 'kubectl delete -f k8s/'
                        } catch (e) {
                            echo 'Error when executing kubectl'
                        }
                        sh 'kubectl apply -f k8s/'
                    }
                }
            }
        }
    }
    post {
        always {
            slackSend channel: '#goal-app',
                      color: COLOR_MAP[currentBuild.currentResult],
                      message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}"
        }
    }
}