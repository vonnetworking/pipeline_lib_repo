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
 * Add poststeps block to the list
 *
 * @author Agile Trailblazers
 * @param name  List name
 *              Usual poststeps list names:
 *                - always:  used to run poststeps anyway (ex: decomission of the dynamic environment)
 *                - success: poststeps to run on pipeline success (ex: email with congratulations or ask for promotion)
 *                - failure: poststeps to run on pipeline failure (ex: pipeline failed message)
 * @param body  Definition of steps to execute
 * @see MPLManager#postStep(String name, Closure body)
 */
def call(String name, Closure body) {
  MPLManager.instance.postStep(name, body)
}
