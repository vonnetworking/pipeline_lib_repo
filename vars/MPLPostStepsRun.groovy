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
 * Run the poststep list in reverse direction
 *
 * @author Agile Trailblazers
 * @param name  List name
 * @see MPLManager#postStepsRun(String name)
 */
def call(String name) {
  MPLManager.instance.postStepsRun(name)
  def errors = MPLManager.instance.getPostStepsErrors(name)
  for( int e in errors )
    println "PostStep '${name}' error: ${e.module}: ${e.error}"
}
