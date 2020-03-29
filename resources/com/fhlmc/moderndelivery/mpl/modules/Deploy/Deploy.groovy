// defines steps to deploy using cdk def; usually defining an ecs delpoyment

if(CFG.'deploy_type' == 'cdk') {
  MPLModule('Cdk Deploy', CFG)
}
else {
  MPLModule('Eks Deploy', CFG)
}
