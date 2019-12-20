/**
 * Sonar Qube
 */

def sonarqubeScannerHome = tool name: 'SonarQubeScanner'
    withSonarQubeEnv('SonarQubeScanner') {
        sh "pwd"
        sh "${sonarqubeScannerHome}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
    }

