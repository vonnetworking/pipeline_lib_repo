//
// Copyright (c) 2018 Grid Dynamics International, Inc. All Rights Reserved
// https://www.griddynamics.com
//
// Classification level: Confidential
//
// $Id: $
// @Project:     MPL
// @Description: Shared Jenkins Modular Pipeline Library
//

package com.griddynamics.devops.mpl.testing

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
