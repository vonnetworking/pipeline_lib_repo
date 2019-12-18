
//MPLModule('Sonar Qube', CFG)
def scannerHome = tool 'SonarQubeScanner';
   sh "${scannerHome}/bin/sonar-scanner"
  
