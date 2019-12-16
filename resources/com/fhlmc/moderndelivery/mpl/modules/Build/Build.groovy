/**
 * Common build module
 */

if( CFG.'build.gradle' ) {
  MPLModule('Gradle Build', CFG)
} else {
  MPLModule('Maven Build', CFG)
}
