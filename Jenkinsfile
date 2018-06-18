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
        sh './gradlew clean build bootJar artifactoryPublish artifactoryDeploy'
    }

    stage('SonarQube analysis') {
        withSonarQubeEnv('sonarqube') {
              // requires SonarQube Scanner for Gradle 2.1+
              // It's important to add --info because of SONARJNKNS-281
              sh './gradlew --info sonarqube'
        }
    }
    
    stage ('Build Ami') {
      git(
           url: "https://github.com/uk-gov-dft/WebOps.git",
           credentialsId: 'username***REMOVED***-github-automation-uk-gov-dft',
           branch: "master"
        )
    }

}
