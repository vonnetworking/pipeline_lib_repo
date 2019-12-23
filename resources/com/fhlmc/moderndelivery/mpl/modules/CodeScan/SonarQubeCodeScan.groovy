/**
 * Simple Sonar Scan
 */


     def sonarqubeScannerHome = tool name: 'SonarQubeScanner'
     withSonarQubeEnv('SonarQubeScanner') {
          sh "${sonarqubeScannerHome}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
        }




