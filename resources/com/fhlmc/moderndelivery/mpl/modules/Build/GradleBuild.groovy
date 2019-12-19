/**
 * Gradle Build
 */
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
  
  def exists = fileExists 'pom.xml'
    if (exists) {
        echo 'Yes build.gradle file exists at root'
        sh """gradle ${gradleTasks}""" 
      /* Publish Junit Test Results */
     // junit 'target/*/*.xml'
    } else {
        echo 'No build.gradle exists'
    }
  
}
