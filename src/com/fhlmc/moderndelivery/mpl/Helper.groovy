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

package com.fhlmc.moderndelivery.mpl

import java.nio.file.Paths

import com.cloudbees.groovy.cps.NonCPS

import org.jenkinsci.plugins.workflow.cps.CpsGroovyShellFactory
import org.jenkinsci.plugins.workflow.cps.CpsThread
import org.jenkinsci.plugins.workflow.libs.LibrariesAction

import hudson.model.Run
import hudson.FilePath

/**
 * Configuration object to provide the config interface
 *
 * @author Agile Trailblazers
 */
abstract class Helper {
  /**
   * Get a new shell with set some specific variables
   *
   * @param vars  Predefined variables to include in the shell
   * @return  cps groovy shell object
   */
  @NonCPS
  static Object getShell(Map vars = [:]) {
    def ex = CpsThread.current().getExecution()
    def shell = new CpsGroovyShellFactory(ex).withParent(ex.getShell()).build()
    vars.each { key, val -> shell.setVariable(key, val) }
    shell
  }

  /**
   * Getting a list of modules for each loaded library with modules data
   * Idea from LibraryAdder.findResource() function
   *
   * @param path  Module resource path
   * @return  list of maps with pairs "module path: module source code"
   *
   * @see org.jenkinsci.plugins.workflow.libs.LibraryAdder#findResources(CpsFlowExecution execution, String name)
   */
  static List getModulesList(String path) {
    def executable = CpsThread.current()?.getExecution()?.getOwner()?.getExecutable()
    if( ! (executable instanceof Run) )
      throw new MPLException('Current executable is not a jenkins Run')

    def action = executable.getAction(LibrariesAction.class)
    if( action == null )
      throw new MPLException('Unable to find LibrariesAction in the current Run')

    def modules = []
    def libs = new FilePath(executable.getRootDir()).child('libs')
    action.getLibraries().each { lib ->
      MPLManager.instance.getModulesLoadPaths().each { modulesPath ->
        def libPath = Paths.get(lib.name, 'resources', modulesPath, path).toString()
        def f = libs.child(libPath)
        if( f.exists() ) modules += [[libPath, f.readToString()]]
      }
    }
    return modules
  }

  /**
   * Helps with merging two maps recursively
   *
   * @param base     map for modification
   * @param overlay  map to override values in the _base_ map
   * @return  modified base map
   */
  static Map mergeMaps(Map base, Map overlay) {
    if( ! (base in Map) ) base = [:]
    if( ! (overlay in Map) ) return overlay
    overlay.each { key, val ->
      base[key] = base[key] in Map ? mergeMaps(base[key], val) : val
    }
    base
  }

  /**
   * Deep copy of the Map or List
   *
   * @param value      value to deep copy
   *
   * @return  value type without any relation to the original value
   */
  @NonCPS
  static cloneValue(value) {
    def out

    if( value in Map )
      out = value.collectEntries { k, v -> [k, cloneValue(v)] }
    else if( value in List )
      out = value.collect { cloneValue(it) }
    else
      out = value

    return out
  }

  /**
   * Helps to run source code in the new shell with predefined vars
   * Also it's overriden by tests to handle the module execution
   *
   * @param src   source code of the module
   * @param path  resource path of the module to track it
   * @param vars  predefined variables for the run
   */
  static void runModule(String src, String path, Map vars = [:]) {
    getShell(vars).evaluate(src, path)
  }

  /**
   * Cut & simplify a stacktrace
   *
   * @param exception  container of the stacktrace
   *
   * @return  List with stack trace elements
   */
  static StackTraceElement[] getModuleStack(Throwable exception) {
    List stack = exception.getStackTrace()

    // For jenkins to remove starting trace items
    if( stack.size() > 0 && stack.last()?.getFileName() == 'Thread.java' ) {
      // Finding the first MPLModule call and cutting the trace
      for( def i = stack.size(); i--; /* inverse for */ ) {
        if( stack[i-1].getFileName()?.endsWith('MPLModule.groovy') )
          break
        else
          stack.remove(i)
      }
    }

    // If the exception are not from the mpl pipeline - need to show at least something
    if( stack.isEmpty() )
      stack = exception.getStackTrace()

    stack as StackTraceElement[]
  }

  /**
   * Looking the latest cause of the module file name and return it's line number
   *
   * @param module_path  MPL module path or module file name
   * @param exception  container of the stacktrace
   *
   * @return  Module line number or null if not found
   */
  static Integer getModuleExceptionLine(String module_path, Throwable exception) {
    List stack = exception.getStackTrace()
    // First try to find the complete module path
    for( def s in stack ) {
      if( s?.getFileName() == module_path )
        return s.getLineNumber()
    }

    // Second try to find at least a module file name
    def module_file = module_path.tokenize('/').last()
    for( def s in stack ) {
      if( s?.getFileName()?.endsWith(module_file) )
        return s.getLineNumber()
    }
    return null
  }

  /**
   * Special function to return exception if someone tries to use MPLConfig in a wrong way
   * Basically used just to be overridden on the unit tests side.
   *
   * @param config  current MPLConfig configuration
   *
   * @return  Set of entries - but only when overridden by unit tests
   */
  static Set configEntrySet(Map config) {
    throw new MPLException('Forbidden to iterate over MPLConfig')
  }
}