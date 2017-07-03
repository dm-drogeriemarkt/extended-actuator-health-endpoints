@Library('pac-pipeline-libraries@1.2.0') _

POM = readPom()

pipeline {

    agent any

    environment {
        COMMIT_MESSAGE = getCommitMessage()
    }

    stages {

        stage('Build') {
            when {
                expression {
                    !(BRANCH_NAME ==~ /development|master/)
                }
            }
            steps {
                mvn 'clean verify'
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    runSonar(POM)
                }
            }
        }

        stage('Build and push to artifactory') {
            when {
                branch 'development'
            }
            steps {
                mvn 'clean deploy'
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    runSonar(POM)
                }
            }
        }

        stage('Release Artifact') {
            when {
                expression {
                    BRANCH_NAME == 'master' && !COMMIT_MESSAGE.startsWith('[maven-release-plugin]')
                }
            }
            steps {
                generateChangelog()
                mvnRelease()
            }
        }
    }
}
