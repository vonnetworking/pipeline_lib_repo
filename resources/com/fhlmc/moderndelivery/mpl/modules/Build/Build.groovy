/**
 * Common build module
 */


def gradle = 'build'

if(CFG.build_tool_gradle) {
  MPLModule('Maven Build', CFG)
} 

/*else {
  MPLModule('Maven Build', CFG)
}*/
