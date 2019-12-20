/**
 * Common code scan module
 */

if( CFG.'scan_type' ){
    MPLModule('Fortify CodeScan', CFG)
}
else{
    MPLModule('SonarQube CodeScan', CFG)
}