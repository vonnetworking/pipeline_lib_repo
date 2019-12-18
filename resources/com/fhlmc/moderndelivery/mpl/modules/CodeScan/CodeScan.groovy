
//MPLModule('Sonar Qube', CFG)


script {
          
          scannerHome = tool 'SonarQubeScanner'
        }
        withSonarQubeEnv('SonarQubeScanner') {
          sh "${scannerHome}/bin/sonar-scanner"
        }
