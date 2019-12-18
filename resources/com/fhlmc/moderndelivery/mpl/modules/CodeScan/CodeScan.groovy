
//MPLModule('Sonar Qube', CFG)


script {
     def sonarqubeScannerHome = tool name: 'SonarQubeScanner'
     withSonarQubeEnv('SonarQubeScanner') {
          sh "${sonarqubeScannerHome}/bin/sonar-scanner"
        }
  }
