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
                script {
                    stage('Initial Install') {
                        standalone_install_build = build(
                            job: 'QE/library/installtotot'
                        )
                    }
                }
            }
        }

        stage('Load data') {
            steps {
                script {
                    stage('Load data') {
                        build(
                            job: 'QE/library/load'
                        )
                    }
                }
            }
        }

        stage('Backup Instance') {
            steps {
                script {
                    stage('Backup Instance') {
                        build(
                            job: 'QE/library/backup'
                        )
                    }
                }
            }
        }

        stage('Wipe and Reinstall Tower') {
            steps {
                script {
                    stage('Wipe and Reinstall Tower') {
                        build(
                            job: 'QE/library/instaltotot'
                        )
                    }
                }
            }
        }

        stage('Restore backup') {
            steps {
                script {
                    stage('Restore backup') {
                        build(
                            job: 'QE/library/restore'
                        )
                    }
                }
            }
        }

        stage('Verify data integrity') {
            steps {
                script {
                    stage('Verify data integrity') {
                        build(
                            job: 'QE/library/verify'
                        )
                    }
                }
            }
        }
    }

}
