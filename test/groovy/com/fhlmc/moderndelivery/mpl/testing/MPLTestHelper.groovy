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

package com.fhlmc.moderndelivery.mpl.testing

import com.lesfurets.jenkins.unit.PipelineTestHelper
import com.lesfurets.jenkins.unit.MethodSignature

@groovy.transform.InheritConstructors
class MPLTestHelper extends PipelineTestHelper {
  public getLibraryConfig() {
    gse.getConfig()
  }
  public getLibraryClassLoader() {
    gse.groovyClassLoader
  }

  void registerAllowedMethod(MethodSignature methodSignature, Closure closure) {
    if( isMethodAllowed(methodSignature.name, methodSignature.args) )
      return // Skipping methods already existing in the list
    allowedMethodCallbacks.put(methodSignature, closure)
  }
}
