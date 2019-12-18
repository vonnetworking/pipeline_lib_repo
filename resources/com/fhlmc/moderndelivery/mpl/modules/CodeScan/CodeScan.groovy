
//MPLModule('Sonar Qube', CFG)
def scannerHome = tool 'SonarQubeScanner';
     withSonarQubeEnv('SonarQubeScanner') { // If you have configured more than one global server connection, you can specify its name
       sh "${scannerHome}/bin/sonar-scanner"
     }
