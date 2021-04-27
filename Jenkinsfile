pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '--privileged -v /server/scripts/.m2:/root/.m2'
        }
    }
    parameters { string(name: 'DEPLOY_ENV', defaultValue: 'harbor', description: '发布的环境:harbor本地harbor仓库 local本地开发环境') }
    stages {
        stage('Build') {
            steps {
                sh "mvn -DskipTests -s /root/.m2/settings-docker.xml clean package -P ${params.DEPLOY_ENV}"
                sh "mvn -DskipTests -s /root/.m2/settings-docker.xml -f ./bizlog-service/pom.xml docker:build docker:push -P ${params.DEPLOY_ENV}"
            }
        }
    }
}