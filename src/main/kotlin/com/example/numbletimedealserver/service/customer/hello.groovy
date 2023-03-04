pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                sshagent(['git']) {
                    checkout([
                            $class: 'GitSCM',
                            branches: [[name: '*/deploy']],
                            userRemoteConfigs: [[
                                                        url: 'git@github.com:inudev5/NumbleTimeDealServer.git',
                                                        credentialsId: 'b60d7587-b545-4277-92db-d1ccefa19b0a'
                                                ]]
                    ])
                }
            }
        }
        // Other stages here
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage("Package"){
            steps{
                sh './gradlew bootJar'
            }
        }


        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("inust33/myapp:${env.BUILD_NUMBER}")

                    docker.withRegistry('https://registry.hub.docker.com/v2', 'docker_credentials') {
                        docker.image("inust33/myapp:${env.BUILD_NUMBER}").push()
                    }
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                script {
                    sh 'docker stop myapp || true'
                    sh 'docker rm myapp || true'
                    sh "docker run -p 7070:8080 -d --name myapp --network=host inust33/myapp:${env.BUILD_NUMBER}"
                }
            }
        }
        stage("run ngrinder"){
            steps {
                script {
                    sh "docker stop controller || true"
                    sh "docker rm controller || true"
                    sh "docker stop agent || true"
                    sh "docker rm agent || true"
                    sh "docker run -d -v ~/ngrinder-controller:/opt/ngrinder-controller --name controller --network=host -p 9000:80 -p 16001:16001 -p 12000-12009:12000-12009 ngrinder/controller"
                    sh "docker run -d --name agent --network=host --link controller:controller ngrinder/agent"
                }
            }
        }

    }
}