//
// Copyright (c) 2019 Agile Trailblazers, Inc. All Rights Reserved
// https://www.agiletrailblazers.com
//
// Classification level: Confidential
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
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
