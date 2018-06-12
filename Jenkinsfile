def version = "${env.BUILD_NUMBER}"
def REPONAME = "${scm.getUserRemoteConfigs()[0].getUrl()}"

node {

    // Get Artifactory server instance, defined in the Artifactory Plugin administration page.
    def server = Artifactory.server "dftbluebadge"
    // Create an Artifactory Gradle instance.
    def rtGradle = Artifactory.newGradleBuild()
    
    stage('Clone sources') {
      git(
           url: "${REPONAME}",
           credentialsId: 'githubsshkey',
           branch: "${BRANCH_NAME}"
        )
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
}
