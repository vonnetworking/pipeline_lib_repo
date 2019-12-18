/**
 * Simple Sonar Scan
 */


withEnv(["PATH+GRADLE=${tool(CFG.'gradle.tool_version' ?: 'Gradle 5')}/bin"]) {
  def settings = CFG.'gradle.settings_path' ? "-s '${CFG.'gradle.settings_path'}'" : ''
  sh "gradle build"
}


//script {
  //   def sonarqubeScannerHome = tool name: 'SonarQubeScanner'
  //   withSonarQubeEnv('SonarQubeScanner') {
   //       sh "${sonarqubeScannerHome}/bin/sonar-scanner --version"
    //    }
 // }
