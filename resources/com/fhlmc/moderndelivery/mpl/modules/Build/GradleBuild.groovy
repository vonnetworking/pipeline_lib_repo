/**
 * Gradle Build
 */

import com.fhlmc.moderndelivery.mpl.Helper
//import com.fhlmc.moderndelivery.mpl.MPLManager
import com.fhlmc.moderndelivery.mpl.MPLModuleException

def gradleTasks = 'build'
if (CFG.gradle_tasks){
  gradleTasks = CFG.gradle_tasks
}

def gradleVersion = 'Gradle 5'

if (CFG.gradle_version){
	gradleVersion = CFG.gradle_version
}

withEnv(["PATH+GRADLE=${tool(CFG.'gradle.tool_version' ?: """${gradleVersion}""")}/bin"]) {
  def settings = CFG.'gradle.settings_path' ? "-s '${CFG.'gradle.settings_path'}'" : ''
  
  def exists = fileExists 'build.gradle'
    if (exists) {
        echo 'Yes build.gradle file exists at root'
        sh """gradle ${gradleTasks}""" 
      /* Publish Junit Test Results */
     // junit 'target/*/*.xml'
    } else {
        echo 'No build.gradle exists'
       def newex = new MPLModuleException("Found error during execution. No build.gradle exists")
      newex.setStackTrace(Helper.getModuleStack(newex))
      throw newex
    }
  
}
