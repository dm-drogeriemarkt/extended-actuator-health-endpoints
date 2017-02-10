node {
    stage('checkout') {
        checkout scm
    }

    ignoreCommitWithMessage('[maven-release-plugin]') {

        if ("${BRANCH_NAME}" != 'master') {
            stage('build') {
                build()
            }
            stage('sonar') {
                withSonarQubeEnv {
                    sonarAnalysis()
                }
            }
        } else {
            stage('release and deploy') {
                mvnRelease()
            }
        }
    }
}

private boolean isPullRequest() {
    "${BRANCH_NAME}".startsWith("PR")
}

def build() {
    mvn "clean deploy -P instrumentation"
}

def sonarAnalysis() {
    withCredentials([usernamePassword(credentialsId: '814d824d-8d80-424e-b5c4-06223b74b806', passwordVariable: 'sonar.stash.password', usernameVariable: 'sonar.stash.user')]) {
        def sonarCommandline = 'sonar:sonar'
        if (isPullRequest()) {
            def prId = "${BRANCH_NAME}".replace('PR-', '')
            sonarCommandline += " -Dsonar.analysis.mode=preview -Dsonar.stash.notification=true -Dsonar.stash.project=infrastructure -Dsonar.stash.repository=extended-actuator-health-endpoints -Dsonar.stash.pullrequest.id=${prId} -Dsonar.stash.password=${env.'sonar.stash.password'}"
        }
        mvn sonarCommandline
    }
}
