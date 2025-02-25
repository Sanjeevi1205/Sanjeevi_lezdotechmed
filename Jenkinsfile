pipeline {
    agent any
    stages {
        stage('Clone Repo') {
            steps {
                script {
                    echo "Cloning repository..."
                    checkout scm
                }
            }
        }
        stage('Build') {
            steps {
                echo "Building the project..."
            }
        }
        //stage('Test') {
            steps {
                echo "Running tests..."
            }
        }
    }
}
