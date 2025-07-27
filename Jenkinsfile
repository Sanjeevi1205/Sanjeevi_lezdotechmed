@Library('Shared-lib') _

pipeline {
    agent any

    stages {
        stage('Run API Pipeline') {
            steps {
                script {
                    mypipeline(
                        repoUrl: 'https://github.com/Sanjeevi1205/Sanjeevi_lezdotechmed.git'
                    )
                }
            }
        }
    }
}
