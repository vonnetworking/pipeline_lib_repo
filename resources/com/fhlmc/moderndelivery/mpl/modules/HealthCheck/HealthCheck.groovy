/**
 * Health Checker
 */

echo "Toolchain Healthcheck"
echo 'Network..'
// TOOLCHAIN ALIVE STATUS
// Bitbucket
echo 'Bitbucket alive status:'

// Jenkins worker nodes
sh "echo 'Jenkins worker nodes' "
sh "curl -i http://3.16.78.210:8080/jenkins"

// SonarQube
sh "curl -i http://3.16.78.210:9000/sonarqube"

// Artifactory
sh "curl -i 'http://18.191.168.84/artifactory/webapp/#/home'"

// Spinnaker
// sh 'http://18.191.174.144:5601/kibana'

// ELK
//sh "curl -i 'http://18.191.174.144:5601/kibana'"

// Prometheus
sh "curl -i 'http://3.135.198.8:8080/'"

// Jira
sh "curl -i 'http://18.221.185.104:8080/jira'"

// Confluence
sh "curl -i 'http://18.221.185.104:8090/confluence'"

echo 'Tools status'
echo 'Tools..'
echo 'using java:'
sh 'which java'
echo 'using mvn'
sh 'which mvn'
echo 'using gradle'
sh 'which gradle'

echo 'Diskspace and availability'
echo 'Diskspace....'