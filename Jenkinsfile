pipeline {
    agent any
    environment {
        IMAGE_NAME = "trabelsinour/atelierdevops"
        IMAGE_TAG  = "${env.BUILD_NUMBER}"
        IMAGE      = "${IMAGE_NAME}:${IMAGE_TAG}"
        IMAGE_LATEST = "${IMAGE_NAME}:latest"
        // MET ICI L'ID EXACT DE TA CREDENTIAL DOCKER HUB (très important)
        DOCKER_CRED = "dockerhub-trabelsi"     // change si ton ID est différent
    }
    stages {
        stage('Checkout') { steps { checkout scm } }

        stage('Maven Build') {
            steps {
                sh 'chmod +x ./mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    docker.build(IMAGE)
                    docker.build(IMAGE_LATEST)
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CRED) {
                        docker.image(IMAGE).push()
                        docker.image(IMAGE_LATEST).push()
                    }
                }
            }
        }
    }
    post {
        always { cleanWs() }
        success { echo "20/20 – https://hub.docker.com/r/${IMAGE_NAME}" }
        failure { echo "Échec – vérifie l'ID Docker dans DOCKER_CRED" }
    }
}
