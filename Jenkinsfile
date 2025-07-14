pipeline {
    agent any

    environment {
        SONARQUBE = 'SonarQube'  // This should match the name configured under Jenkins -> Manage Jenkins -> Configure System -> SonarQube servers
    }

    tools {
        maven 'Maven 3.8.6' // This should match the Maven tool name configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'GITHUB_CREDENTIALS', url: 'https://github.com/Sanjeevi1205/Sanjeevi_lezdotechmed.git'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE}") {
                    sh 'mvn clean verify sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
    }
}
