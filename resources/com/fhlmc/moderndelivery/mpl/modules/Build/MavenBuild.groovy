/**
 * Simple Maven Build
 */

import com.fhlmc.moderndelivery.mpl.Helper
import com.fhlmc.moderndelivery.mpl.MPLModuleException

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
  def exists = fileExists 'pom.xml'
    if (exists) {
        echo 'Yes pom.xml exists at root'
      sh """mvn -B ${settings} -DargLine='-Xmx1024m -XX:MaxPermSize=1024m' ${mavenGoals}"""  
      /* Publish Junit Test Results if the tests ran*/
      def test_results_exist = fileExists 'target/*/*.xml'
      if (test_results_exist){
      junit 'target/*/*.xml'
      }
    } else {
        def newex = new MPLModuleException("Found error during execution. No pom.xml file exists in the project workspace")
      newex.setStackTrace(Helper.getModuleStack(newex))
      throw newex
    }
  
}



