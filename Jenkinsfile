def build_number = "${env.BUILD_NUMBER}"
def REPONAME = "${scm.getUserRemoteConfigs()[0].getUrl()}"

node {

    stage('Clone sources') {
      git(
           url: "${REPONAME}",
           credentialsId: 'githubsshkey',
           branch: "${BRANCH_NAME}"
        )
     }

    stage('Read Version') {
      def version = readFile('VERSION').trim()
      println "${version}"
    }

    stage ('Gradle build') {
        try {
            sh 'echo $(whoami)'
            sh 'bash -c "source /etc/profile && (npm list gulp -g || npm install -g gulp) && npm install && npm run prod"'
            sh './gradlew clean build bootJar artifactoryPublish artifactoryDeploy'
        }
        finally {
            junit '**/TEST*.xml'
        }
    }

    stage ('OWASP Dependency Check') {
        sh './gradlew dependencyCheckUpdate dependencyCheckAggregate'
        archive (includes: 'build/reports/*.html')
         publishHTML (target: [
          allowMissing: false,
          alwaysLinkToLastBuild: false,
          keepAll: true,
          reportDir: 'build/reports',
          reportFiles: 'dependency-check-report.html',
          reportName: "OWASP Dependency Check"
        ])
    }

    stage('SonarQube analysis') {
        withSonarQubeEnv('sonarqube') {
            def ver = readFile('VERSION').trim()
            echo "Version: " + ver
            // requires SonarQube Scanner for Gradle 2.1+
            // It's important to add --info because of SONARJNKNS-281
            sh "./gradlew --info sonarqube -Dsonar.projectName=la-webapp -Dsonar.projectVersion=${ver} -Dsonar.branch=${BRANCH_NAME}"
        }
    }

    stage("Quality Gate") {
        timeout(time: 5, unit: 'MINUTES') {
            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
                error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
        }
    }

    stage("Acceptance Tests") {
        node('Functional') {
            git(
               url: "${REPONAME}",
               credentialsId: 'dft-buildbot-valtech',
               branch: "${BRANCH_NAME}"
            )

            timeout(time: 10, unit: 'MINUTES') {
                try {
                    sh 'bash -c "echo $PATH && cd acceptance-tests && ./run-regression.sh"'
                }
                finally {
                    archiveArtifacts allowEmptyArchive: true, artifacts: '**/docker.log'
                    junit '**/TEST*.xml'
                }
            }
        }
    }
}
