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
        def buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'build'
        server.publishBuildInfo buildInfo
    }
    
    stage ('Artifactory Upload') {

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
        server.upload(uploadSpec)
    }
}
