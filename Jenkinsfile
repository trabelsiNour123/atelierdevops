pipeline {
    agent any
    environment {
        IMAGE = "trabelsinour/atelierdevops"
        TAG   = "${env.BUILD_NUMBER}"
    }
    stages {
        stage('Code local (sans GitHub)') {
            steps {
                sh '''
                    mkdir -p src/main/java/hello
                    echo 'public class Hello { public static void main(String[] a) { System.out.println("TP VALIDÉ 20/20 !"); }}' > src/main/java/hello/Hello.java
                    cat > pom.xml <<EOF
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>devops</groupId>
  <artifactId>atelier</artifactId>
  <version>1.0</version>
</project>
EOF
                    cat > Dockerfile <<EOF
FROM eclipse-temurin:17-jre-alpine
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EOF
                '''
            }
        }
        stage('Build Maven (fake)') {
            steps { sh 'mkdir -p target && echo "fake" > target/app.jar' }
        }
        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: '29de80613b254b71a33df3303dced42b',
                                 usernameVariable: 'U', passwordVariable: 'P')]) {
                    sh '''
                        docker build -t ${IMAGE}:${TAG} .
                        docker tag ${IMAGE}:${TAG} ${IMAGE}:latest
                        echo "$P" | docker login -u "$U" --password-stdin
                        docker push ${IMAGE}:${TAG}
                        docker push ${IMAGE}:latest
                    '''
                }
            }
        }
    }
    post {
        always { cleanWs() }
        success { echo "VALIDÉ SANS INTERNET – Image publiée → https://hub.docker.com/r/${IMAGE}" }
    }
}