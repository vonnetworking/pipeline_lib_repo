/**
 * Simple Sonar Scan
 */

script {
     def sonarqubeScannerHome = tool name: 'SonarQubeScanner'
     withSonarQubeEnv('SonarQubeScanner') {
          sh "${sonarqubeScannerHome}/bin/sonar-scanner --version"
        }
}




