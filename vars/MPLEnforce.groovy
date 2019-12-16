//
// Copyright (c) 2019 Agile Trailblazers, Inc. All Rights Reserved
// https://www.agiletrailblazers.com
//
// Classification level: Confidential
//
// $Id: $
// @Project:     MPL
// @Description: Shared Jenkins Modular Pipeline Library
//

import com.fhlmc.moderndelivery.mpl.MPLManager

/**
 * Enables enforced mode of the MPL library
 *
 * @author Agile Trailblazers
 * @param modules  Module names available to be overriden on the project level
 */
def call(List modules) {
  MPLManager.instance.enforce(modules)
}
