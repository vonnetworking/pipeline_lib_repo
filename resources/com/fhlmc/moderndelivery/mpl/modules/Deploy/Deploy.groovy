// defines steps to deploy using cdk def; usually defining an ecs delpoyment

if(CFG.'deploy_type' == 'cdk') {
  MPLModule('CDK Deploy', CFG)
}
else {
  MPLModule('EKS Deploy', CFG)
}
