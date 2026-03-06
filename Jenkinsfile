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
                echo 'Building project...'
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh "${DOCKER_CMD} build -t ${DOCKER_IMAGE} ."
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                sh """
                    export DOCKER_CMD="${DOCKER_CMD}"
                    export DOCKER_IMAGE="${DOCKER_IMAGE}"
                    export DOCKER_CONTAINER="${DOCKER_CONTAINER}"
                    \$DOCKER_CMD stop \$DOCKER_CONTAINER 2>/dev/null || true
                    \$DOCKER_CMD rm \$DOCKER_CONTAINER 2>/dev/null || true
                    \$DOCKER_CMD run -d --name \$DOCKER_CONTAINER -p 8082:8080 \
                        -e SPRING_DATASOURCE_URL='jdbc:mysql://${HOST_IP}:3307/family_finance?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true' \
                        -e SPRING_DATASOURCE_USERNAME=root \
                        -e SPRING_DATASOURCE_PASSWORD=root \
                        \$DOCKER_IMAGE
                """
            }
        }
        stage('Health Check') {
            steps {
                echo 'Checking application status...'
                sh """
                    export DOCKER_CMD="${DOCKER_CMD}"
                    export DOCKER_CONTAINER="${DOCKER_CONTAINER}"
                    sleep 25
                    \$DOCKER_CMD ps | grep \$DOCKER_CONTAINER
                    \$DOCKER_CMD logs \$DOCKER_CONTAINER | tail -5
                """
            }
        }
    }
    post {
        success {
            echo '=== Deployment successful! ==='
            echo "Access: http://localhost:8082"
        }
        failure {
            echo '=== Deployment failed! ==='
        }
    }
}
