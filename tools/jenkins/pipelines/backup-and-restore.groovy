pipeline {

    agent none

    stages {

        stage('Display Information') {
            steps {
                echo "Display Information"
            }
        }

        stage('Initial Setup') {
            steps {
                checkout(
                    $class: 'GitSCM',
                    url: 'https://github.com/Spredzy/jenkins-repoa'
                )

                sh './tools/jenkins/scripts/setup.sh'
            }
        }

        stage('Initial Install') {
            steps {
                sh './tools/jenkins/scripts/install.sh'
            }
        }

        stage('Load data') {
            steps {
                sh './tools/jenkins/scripts/load.sh'
            }
        }

        stage('Backup Instance') {
            steps {
                sh './tools/jenkins/scripts/backup.sh'
            }
        }

        stage('Wipe and Reinstall Tower') {
            steps {
                sh './tools/jenkins/scripts/install.sh'
            }
        }

        stage('Restore backup') {
            steps {
                sh './tools/jenkins/scripts/restore.sh'
            }
        }

        stage('Verify data integrity') {
            steps {
                sh './tools/jenkins/scripts/verify.sh'
            }
        }
    }

}
