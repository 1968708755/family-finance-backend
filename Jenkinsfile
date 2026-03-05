pipeline {
    agent any

    environment {
        MAVEN_HOME = '/var/jenkins_home/tools/maven'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        DOCKER_IMAGE = "family-finance-backend:${BUILD_NUMBER}"
        DOCKER_CONTAINER = "family-finance-app"
    }

    stages {
        stage('Build') {
            steps {
                echo '构建项目...'
                sh '''
                    echo "Maven 版本:"
                    mvn -version
                    echo "开始构建..."
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '构建 Docker 镜像...'
                sh """
                    docker build -t ${DOCKER_IMAGE} .
                    docker images | grep family-finance
                """
            }
        }

        stage('Deploy') {
            steps {
                echo '部署应用...'
                sh """
                    # 停止并删除旧容器
                    docker stop ${DOCKER_CONTAINER} 2>/dev/null || true
                    docker rm ${DOCKER_CONTAINER} 2>/dev/null || true

                    # 启动新容器
                    docker run -d \
                        --name ${DOCKER_CONTAINER} \
                        -p 8082:8080 \
                        --network bridge \
                        ${DOCKER_IMAGE}

                    echo "容器已启动: ${DOCKER_CONTAINER}"
                """
            }
        }

        stage('Health Check') {
            steps {
                echo '等待应用启动...'
                sh '''
                    sleep 15
                    echo "检查容器状态..."
                    docker ps | grep ${DOCKER_CONTAINER}
                    echo "检查应用日志..."
                    docker logs ${DOCKER_CONTAINER} | tail -30
                '''
            }
        }
    }

    post {
        success {
            echo '=== 部署成功！==='
            echo "访问地址: http://localhost:8082"
        }
        failure {
            echo '=== 部署失败！==='
            sh """
                docker stop ${DOCKER_CONTAINER} 2>/dev/null || true
                docker rm ${DOCKER_CONTAINER} 2>/dev/null || true
            """
        }
    }
}
