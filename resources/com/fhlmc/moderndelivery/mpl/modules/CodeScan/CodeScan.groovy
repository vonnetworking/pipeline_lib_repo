
//MPLModule('Sonar Qube', CFG)


script {
          
          scannerHome = tool 'SonarQubeScanner'
        }
        withSonarQubeEnv('SonarQube Scanner') {
          sh "${scannerHome}/bin/sonar-scanner"
        }
