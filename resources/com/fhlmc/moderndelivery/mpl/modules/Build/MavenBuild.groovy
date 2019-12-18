/**
 * Simple Maven Build
 */
def mavenGoals = 'clean install'
if (CFG.'maven.goals'){
  mavenGoals = CFG.'maven.goals'
}
/* Default build on Maven 3 */

def str2 = "modp_jenkins_worker_1" 
if (config.agent_label ==str2){
withEnv(["PATH+MAVEN=${tool(CFG.'maven.tool_version' ?: 'Maven 3')}/bin"]) {
  def settings = CFG.'maven.settings_path' ? "-s '${CFG.'maven.settings_path'}'" : ''
  sh """mvn -B ${settings} -DargLine='-Xmx1024m -XX:MaxPermSize=1024m' ${mavenGoals}"""
}

}


/*'${CFG.'maven.Build.tool_version'}' */
