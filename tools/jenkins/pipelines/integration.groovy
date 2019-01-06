pipeline {

    agent none

    stages {

        stage('Display Information') {
            steps {
                echo "Display Information"
            }
        }

        stage('Install') {
            steps {
                echo "Initial Install"
            }
        }

        stage('E2E Test') {
            steps {
                echo "e2e test"
            }
        }

        stage('Integration Test') {
            steps {
                echo "integration test"
            }
        }
    }

}
