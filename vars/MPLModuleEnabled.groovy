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
 * Determine is module enabled in the pipeline or not
 *
 * @author Agile Trailblazers
 * @param name  Module name (stage name by default)
 * @return  Boolean module enabled or not
 */
def call(String name = env.STAGE_NAME) {
  MPLManager.instance.moduleEnabled(name)
}
