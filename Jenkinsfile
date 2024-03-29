def build_number = "${env.BUILD_NUMBER}"
def REPONAME = "${scm.getUserRemoteConfigs()[0].getUrl()}"

node {

    stage('Clone sources') {
      cleanWs()
      git(
           url: "${REPONAME}",
           credentialsId: 'username***REMOVED***-github-automation-uk-gov-dft',
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
            sh './gradlew --no-daemon --profile --configure-on-demand clean build bootJar artifactoryPublish artifactoryDeploy'
            sh 'mv build/reports/profile/profile-*.html build/reports/profile/index.html'
            stash includes: 'build/**/*', name: 'build'
        }
        finally {
            junit '**/TEST*.xml'
        }
        publishHTML (target: [
          allowMissing: false,
          alwaysLinkToLastBuild: false,
          keepAll: true,
          reportDir: 'build/reports/profile',
          reportFiles: 'index.html',
          reportName: "Gradle Profile Report"
        ])
    }

    stage ('DockerPublish') {
      node('Functional') {
        git(
           url: "${REPONAME}",
           credentialsId: 'dft-buildbot-valtech',
           branch: "${BRANCH_NAME}"
        )

        unstash 'build'
      
        sh 'ls -la'

        withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_TOKEN')]) {
          sh '''
            ls -la build/libs
            curl -s -o docker-publish.sh -H "Authorization: token ${GITHUB_TOKEN}" -H 'Accept: application/vnd.github.v3.raw' -O -L https://raw.githubusercontent.com/uk-gov-dft/shell-scripts/master/docker-publish.sh
            ls -la
            bash docker-publish.sh
          '''
        }
      }
    }

    stage ('OWASP Dependency Check') {
        sh './gradlew dependencyCheckUpdate dependencyCheckAggregate'

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

            timeout(time: 20, unit: 'MINUTES') {
                withEnv(["BASE_SELENIUM_URL=http://localhost:8080", "BASE_MANAGEMENT_URL=http://localhost:8081"]) {
                  withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_TOKEN')]) {
                    try {
                        sh '''
                          cd acceptance-tests
                          curl -s -o run-regression-script.sh -H "Authorization: token ${GITHUB_TOKEN}" -H 'Accept: application/vnd.github.v3.raw' -O -L https://raw.githubusercontent.com/uk-gov-dft/shell-scripts/master/run-regression.sh

                          chmod +x run-regression-script.sh
                          ./run-regression-script.sh
                        '''
                    }
                    finally {
                        archiveArtifacts allowEmptyArchive: true, artifacts: '**/docker.log'
                        junit '**/TEST*.xml'
                    }
                  }
                }
            }
        }
    }
}
