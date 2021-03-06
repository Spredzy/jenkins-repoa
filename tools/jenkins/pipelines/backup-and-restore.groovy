pipeline {

    agent any

    parameters {
        choice(
            name: 'TOWER_VERSION',
            description: 'Tower version to deploy',
            choices: ['devel', '3.4.0', '3.3.4', '3.3.3', '3.3.2', '3.3.1', '3.3.0',
                      '3.2.8', '3.2.7', '3.2.6', '3.2.5', '3.2.4', '3.2.3', '3.2.2',
                      '3.2.1', '3.2.0', '3.1.8']
        )
        choice(
            name: 'ANSIBLE_VERSION',
            description: 'Ansible version to deploy within Tower install',
            choices: ['devel', 'stable-2.7', 'stable-2.6', 'stable-2.5',
                      'stable-2.4', 'stable-2.3']
        )
        choice(
            name: 'SCENARIO',
            description: 'Deployment scenario for Tower install',
            choices: ['standalone', 'cluster']
        )
        choice(
            name: 'PLATFORM',
            description: 'The OS to install the Tower instance on',
            choices: ['rhel-7.6-x86_64', 'rhel-7.5-x86_64', 'rhel-7.4-x86_64',
                      'ol-7.6-x86_64', 'centos-7.latest-x86_64',
                      'ubuntu-16.04-x86_64', 'ubuntu-14.04-x86_64']
        )
        booleanParam(
            name: 'BUNDLE',
            description: 'Should the bundle version be used',
            defaultValue: false
        )
        string(
            name: 'TOWERQA_BRANCH',
            description: 'ansible/tower-qa branch to use',
            defaultValue: 'origin/devel'
        )
        string(
            name: 'DEPLOYMENT_NAME',
            description: 'Deployment name. Will match VM name being spawned in the cloud',
            defaultValue: 'jenkins-tower-install'
        )
        booleanParam(
            name: 'VERBOSE',
            description: 'Should the deployment be verbose',
            defaultValue: false
        )
        booleanParam(
            name: 'CLEAN_DEPLOYMENT_BEFORE_JOB_RUN',
            description: 'Should the deployment be cleaned before job is run',
            defaultValue: true
        )
        booleanParam(
            name: 'CLEAN_DEPLOYMENT_AFTER_JOB_RUN',
            description: 'Should the deployment be cleaned after job is run',
            defaultValue: false
        )
        booleanParam(
            name: 'CLEAN_DEPLOYMENT_ON_JOB_FAILURE',
            description: 'Should the deployment be cleaned if job fails',
            defaultValue: true
        )
    }

    options {
        timestamps()
    }

    stages {

        stage('Display Information') {
            steps {
                echo """Tower Version: ${params.TOWER_VERSION}\n \
Ansible Version: ${params.ANSIBLE_VERSION}"""
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
                archiveArtifacts(
                    artifacts: 'toto.date'
                )
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
