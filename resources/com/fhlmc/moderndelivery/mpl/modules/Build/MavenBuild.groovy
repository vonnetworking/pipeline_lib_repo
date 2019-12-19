/**
 * Simple Maven Build
 */
def mavenGoals = 'clean install'
if (CFG.'maven_goals'){
  mavenGoals = CFG.'maven_goals'
}
/* Default build on Maven 3 */

def mavenVersion = 'Maven 3'
if (CFG.'maven_tool_version'){
  mavenVersion =CFG.'maven_tool_version'
}

withEnv(["PATH+MAVEN=${tool(CFG.'maven.tool_version' ?: """${mavenVersion}""")}/bin"]) {
  def settings = CFG.'maven.settings_path' ? "-s '${CFG.'maven.settings_path'}'" : ''
  sh "mvn -v"
  sh """mvn -B ${settings} -DargLine='-Xmx1024m -XX:MaxPermSize=1024m' ${mavenGoals}""" 
  junit 'target/*/*.xml'
  sh "cat target/*/*.xml"
}



