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
