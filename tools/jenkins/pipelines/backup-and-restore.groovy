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

        stage('Backup Instance') {
            steps {
                echo "Backup Instance"
            }
        }

        stage('Wipe and Reinstall Tower') {
            steps {
                echo "Wipe and Reinstall Tower"
            }
        }

        stage('Restore backup') {
            steps {
                echo "Restore backup"
            }
        }

        stage('Verify data integrity') {
            steps {
                echo "Verify data integrity"
            }
        }
    }

}
