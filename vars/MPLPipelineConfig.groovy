//
// Copyright (c) 2019 Agile Trailblazers, Inc. All Rights Reserved
// https://www.agiletrailblazers.com
//
// $Id: $
// @Project:     MPL
// @Description: Shared Jenkins Modular Pipeline Library
//

import com.fhlmc.moderndelivery.mpl.Helper
import com.fhlmc.moderndelivery.mpl.MPLManager
import com.fhlmc.moderndelivery.mpl.MPLException

/**
 * MPL pipeline helper to provide the default modules configuration
 *
 * @author Agile Trailblazers
 *
 * @param body      Configuration for the pipeline
 * @param defaults  Default configuration from the pipeline definition
 * @param overrides Mandatory settings for the pipeline that will override config settings
 *
 * @return MPLManager singleton object
 */
def call(body, Map defaults = [:], Map overrides = [:]) {
  def config = defaults

  // Merging configs
  if( body in Closure ) {
    // This logic allow us to use configuration closures instead of maps
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config

    // Make sure the global variables will be available in the config closure
    config.env = env
    config.params = params
    config.currentBuild = currentBuild

    // Here we executing the closure to update the pipeline defaults with the closure values
    body()

    // Removing the global variables from the config
    config.remove('env')
    config.remove('params')
    config.remove('currentBuild')
  } else if( body in Map ) {
    Helper.mergeMaps(config, body)
  } else
    throw new MPLException("Unsupported MPL pipeline configuration type provided: ${body}")

  Helper.mergeMaps(config, overrides)

  // Init the MPL Pipeline
  MPLManager.instance.init(config)
}
