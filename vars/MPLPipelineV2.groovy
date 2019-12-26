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
      HealthCheck: [:],
      Build: [:],
      CodeScan: [:],
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

      stage( 'HealthCheck' ) {
        if ( expression { MPLModuleEnabled() } ){
          MPLModule()
        }
      }
      /* stage( 'Checkout' ) {
        if ( expression { MPLModuleEnabled() } ){
          MPLModule()
        }
      } */

      stage( 'Configure' ) {
          script {

            println "current workspace is ${workspace}"
            sh "ls -al"
            /* def configFile = "${env.WORKSPACE}" + "/mdbuild.config"
            def config = readFile("mdbuild.config")
            Map configMap = evaluate(config) */
            def config = readYaml file:"manifest.yaml"
            echo config
            Map configMap = evaluate(config)
            echo configMap
            MPL = MPLPipelineConfig(configMap, [
                    agent_label: '',
                    maven_tool_version: '',
                    modules: [
                            Checkout: [:],
                            HealthCheck: [:],
                            Build: [:],
                            CodeScan: [:],
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
      stage( 'CodeScan' ) {
        if ( expression { MPLModuleEnabled() } ){
          MPLModule()
        }
      }
     /* stage( 'Deploy' ) {
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }
      stage( 'Test' ) {
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }*/
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
