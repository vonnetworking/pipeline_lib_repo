/**
 * Common build module
 */

if( CFG.'build.maven' ) {
  MPLModule('Maven Build', CFG)
} else {
  MPLModule('Gradle Build', CFG)
}
