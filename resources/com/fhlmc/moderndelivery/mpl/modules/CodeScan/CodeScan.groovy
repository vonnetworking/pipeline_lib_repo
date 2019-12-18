
//MPLModule('Sonar Qube', CFG)


script {
          
          def sonarqubeScannerHome = tool name: 'SonarQubeScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'

        
        withSonarQubeEnv('SonarQubeScanner') {
          sh "${sonarqubeScannerHome}/bin/sonar-scanner"
        }
  
  }
