//
// Copyright (c) 2019 Agile Trailblazers, Inc. All Rights Reserved
// https://www.agiletrailblazers.com
//
//
// $Id: $
// @Project:     MPL
// @Description: Shared Jenkins Modular Pipeline Library
//

/**
 * Basic MPL pipeline
 * Shows pipeline with basic stages and modules of the MPL library
 *
 * @author Agile Trailblazers
 */
import jenkins.model.Jenkins

def call(body) {

  def MPL = MPLPipelineConfigV2(body, [
    agent_label: '',
    maven_tool_version: '',
    modules: [
      Checkout: [:],
      Build: [:],
      Deploy: [:],
      Test: [:]
    ]
  ])

  if(MPL.getAgentLabel() == '')
  {
    MPL.setAgentLabel('modp_jenkins_worker_1')
  }

    /* options {
      skipDefaultCheckout(true)
    } */

      stage( 'Configure' ) {
          script {

            println "current workspace is ${workspace}"
            sh "ls -al"
            /* def configFile = "${env.WORKSPACE}" + "/mdbuild.config"
            def config = readFile("mdbuild.config")
            Map configMap = evaluate(config) */
            def configMap = readYaml file:"pipeline_config.yaml"
            MPL = MPLPipelineConfig(configMap, [
                    agent_label: '',
                    maven_tool_version: '',
                    modules: [
                            Checkout: [:],
                            HealthCheck: [:],
                            Build: [:],
                            Deploy: [:],
                            Test: [:]
                    ]
            ])
          }
        }

      stage( 'Build' ) {
        if ( expression { MPLModuleEnabled() } ){
          MPLModule()
        }
      }
     stage( 'Deploy' ) {
        if ( expression { MPLModuleEnabled() } ){
          MPLModule()
        }
      }
      stage( 'Test' ) {
        if ( expression { MPLModuleEnabled() } ){
          MPLModule()
        }
      }
    }
    post {
      always {
        MPLPostStepsRun('always')
      }
      success {
        MPLPostStepsRun('success')
      }
      failure {
        MPLPostStepsRun('failure')
      }
    }
