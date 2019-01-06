pipeline {

    agent none

    stages {

        stage('Display Information') {
            steps {
                echo "Display Information"
            }
        }

        stage('Initial Install') {
            steps {
                echo "Initial Install"
            }
        }

        stage('Load data') {
            steps {
                echo "Loading data"
            }
        }

        stage('Upgrade') {
            steps {
                echo "Upgrade"
            }
        }

        stage('Verify data integrity') {
            steps {
                echo "Verify data integrity"
            }
        }
    }

}
