/**
 * Common build module
 */


def gradle = 'build'

//if(CFG.'build_tool_gradle') {
def exists = fileExists 'build.gradle'
 if (exists) {
  MPLModule('Gradle Build', CFG)
} 

else {
  MPLModule('Maven Build', CFG)
}

