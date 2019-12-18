/**
 * Common build module
 */


def gradle = 'build'

if(CFG.gradle) {
  MPLModule('Gradle Build', CFG)
} 

/*else {
  MPLModule('Maven Build', CFG)
}*/
