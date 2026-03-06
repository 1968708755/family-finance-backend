pipeline {
    agent any
    environment {
        MAVEN_HOME = '/var/jenkins_home/tools/maven'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        DOCKER_IMAGE = "family-finance-backend:${BUILD_NUMBER}"
        DOCKER_CMD = "/usr/local/bin/docker"
        DOCKER_CONTAINER = "family-finance-app"
        HOST_IP = "10.71.80.15"
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo 'Building image...'
                sh "${DOCKER_CMD} build -t ${DOCKER_IMAGE} ."
            }
        }
        stage('Deploy') {
            steps {
                sh """
                    ${DOCKER_CMD} stop ${DOCKER_CONTAINER} 2>/dev/null || true
                    ${DOCKER_CMD} rm ${DOCKER_CONTAINER} 2>/dev/null || true
                    ${DOCKER_CMD} run -d --name ${DOCKER_CONTAINER} -p 8082:8080                         -e SPRING_DATASOURCE_URL='jdbc:mysql://${HOST_IP}:3307/family_finance?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC'                         -e SPRING_DATASOURCE_USERNAME=root                         -e SPRING_DATASOURCE_PASSWORD=root                         ${DOCKER_IMAGE}
                """
            }
        }
        stage('Health Check') {
            steps {
                sh '''
                    sleep 25
                    curl -f http://localhost:8082/api/users
                '''
            }
        }
    }
    post {
        success {
            echo "=== Success! Access: http://localhost:8082 ==="
        }
    }
}
