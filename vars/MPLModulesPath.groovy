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
 * Easy way to set additional path with library modules
 *
 * @author Agile Trailblazers
 * @param path  path to the library modules
 */
def call(String path) {
  MPLManager.instance.addModulesLoadPath(path)
}
