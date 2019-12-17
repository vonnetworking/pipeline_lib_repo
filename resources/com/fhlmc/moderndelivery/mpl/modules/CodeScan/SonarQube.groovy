/**
 * Sonar Qube
 */

def scannerHome = tool 'sonar';
withSonarQubeEnv('sonar') { // If you have configured more than one global server connection, you can specify its name
    sh "${scannerHome}/bin/sonar-scanner"
}