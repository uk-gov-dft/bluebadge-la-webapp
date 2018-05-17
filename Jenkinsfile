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
    

    stage ('Artifactory configuration') {
        // Tool name from Jenkins configuration
        rtGradle.tool = "Gradle-4.6"
        // Set Artifactory repositories for dependencies resolution and artifacts deployment.
            
        rtGradle.deployer repo:'gradle-release-local', server: server
        rtGradle.resolver repo:'gradle-release', server: server
    }



    stage ('Gradle build') {

      def uploadSpec = """{
        "files": [
        {
          "pattern": "client/build/libs/*.jar",
          "target": "gradle-release-local/",
          "regexp": "false",
          "recursive": "false"
        },
        {
          "pattern": "model/build/libs/*.jar",
          "target": "gradle-release-local/",
          "regexp": "false",
          "recursive": "false"
        },
        {
          "pattern": "server/build/libs/*.jar",
          "target": "gradle-release-local/",
          "regexp": "false",
          "recursive": "false"
        },
        {
          "pattern": "build/libs/*.jar",
          "target": "gradle-release-local/",
          "regexp": "false",
          "recursive": "false"
        }
        ]
        }"""
        def buildInfo1  = rtGradle.run buildFile: 'build.gradle', tasks: 'wrapper build'
        def buildInfo2 = server.upload(uploadSpec)
        buildInfo1.append buildInfo2
        server.publishBuildInfo buildInfo1
    }

        stage('SonarQube analysis') {
        withSonarQubeEnv('sonarqube') {
              // requires SonarQube Scanner for Gradle 2.1+
              // It's important to add --info because of SONARJNKNS-281
              sh './gradlew --info sonarqube'
        }
    }
}