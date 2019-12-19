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
def call(body) {
  def MPL = MPLPipelineConfig(body, [
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

  pipeline {
    agent {
      label MPL.agentLabel
    }
    options {
      skipDefaultCheckout(true)
    }
    stages {
      stage( 'HealthCheck' ) {
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
      }
    }
      stage( 'Checkout' ) {
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }
      stage( 'Build' ) {
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }
      stage( 'CodeScan' ) {
<<<<<<< HEAD
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }
      stage( 'Deploy' ) {
=======
>>>>>>> f427710f63f0c3d4f914200ecbbc6c5e41aa0191
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }
    /*  stage( 'Deploy' ) {
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
  }
}
