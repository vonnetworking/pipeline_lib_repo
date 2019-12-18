/**
 * Simple Maven Build
 */
def mavenGoals = 'clean install'
if (CFG.'maven_goals'){
  mavenGoals = CFG.'maven_goals'
}
/* Default build on Maven 3 */

withEnv(["PATH+MAVEN=${tool(CFG.'maven.tool_version' ?: CFG.'maven_tool_version')}/bin"]) {
  def settings = CFG.'maven.settings_path' ? "-s '${CFG.'maven.settings_path'}'" : ''
  sh """mvn -B ${settings} -DargLine='-Xmx1024m -XX:MaxPermSize=1024m' ${mavenGoals}"""
  sh "mvn -v"
}


