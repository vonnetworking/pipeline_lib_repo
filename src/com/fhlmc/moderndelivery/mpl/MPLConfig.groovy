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

import com.cloudbees.groovy.cps.NonCPS

/**
 * Configuration object to provide the config interface
 *
 * @author Agile Trailblazers
 */
public class MPLConfig implements Map, Serializable {
  /** Configuration Map or List storage */
  private Map config = [:]

  /**
   * Creating new MPLConfig
   *
   * It's impossible to create a new object of MPLConfig using consructor
   * because it's throwing com.cloudbees.groovy.cps.impl.CpsCallableInvocation
   * since constructors can't be CPS-transformed
   *
   * @param config  Map or List with configuration
   *
   * @return  MPLConfig object
   */
  @NonCPS
  static public MPLConfig create(cfg) {
    new MPLConfig(cfg)
  }

  /**
   * Clone of the existing MPLConfig
   *
   * @return  MPLConfig object clone
   */
  @NonCPS
  public MPLConfig clone() {
    new MPLConfig(this.@config)
  }

  /**
   * MPLConfig constructor
   *
   * Private to disallow breaking the pipeline
   */
  private MPLConfig(cfg = [:]) {
    this.@config = Helper.cloneValue(cfg)
  }

  /**
   * It's forbidden to use the MPLConfig as a regular Map.
   * CFG is just a config place, you can't iterate over it.
   * The module should know the key it's trying to use, not to process everything.
   */
  public Object remove(Object key) {}
  public Collection values() {}
  public Set keySet() {}
  public void clear() {}
  public boolean containsKey(Object key) {}
  public boolean containsValue(Object val) {}
  public void putAll(Map map) {}

  public int size() { this.@config.size() }
  public boolean isEmpty() { this.@config.isEmpty() }
  public Set entrySet() { Helper.configEntrySet(this.@config) }
  
  /**
   * Get a value copy of the provided config key path
   *
   * Warning: it's not a good idea to use the method directly as
   *          `CFG.get('key')` - please use `CFG.'key'` instead.
   *          In the future versions direct access could be removed
   *
   * Allowed path tokens:
   *  string - almost any charset not containing dot, key of the Map
   *  number - index of a List, if target is not Map
   *
   * @param key_path - path to the desired config value separated by dot
   * @return value of the key path or null if not found
   */
  public Object get(Object key_path) {
    def key_list = key_path.tokenize(".")

    def value = this.@config
    for( def key in key_list ) {
      if( value in Map )
        value = value[key]
      else if( value in List && key.isInteger() && key.toInteger() >= 0 )
        value = value[key.toInteger()]
      else
        value = null

      if( value == null )
        break
    }
    return Helper.cloneValue(value)
  }

  /**
   * Set value for the provided config key path
   *
   * Warning: it's not a good idea to use the method directly as
   *          `CFG.put('key', 'value')` - please use `CFG.'key' = 'value'` instead
   *          In the future versions direct access could be removed
   *
   * Allowed path tokens:
   *  string - almost any charset not containing dot, key of the Map
   *  number - index of a List, if target is not Map
   *
   * Not existing key will be created as Map (even if number key is provided)
   * Exception could be thrown if:
   *  - you trying to set sublevel of the existing key which is not the required type:
   *    + List requires positive integer: string keys will cause exception
   *    + Non-container variables (of course)
   *
   * @param key_path - path to the desired config value separated by dot
   */
  public Object put(Object key_path, Object val) {
    def key_list = key_path.tokenize(".")

    def key, parent
    def value = this.@config
    for( def i = 0; i < key_list.size(); i++ ) {
      key = key_list[i]
      parent = value

      if( parent in List ) {
        if( key.isInteger() && key.toInteger() >= 0 )
          key = key.toInteger()
        else
          throw new MPLException("Invalid config key path '${key_list[i] = '<'+key_list[i]+'>'; key_list.join('.')}': marked key of the list '${key_list[i-1]}' is not a positive integer")
      }

      if( parent in Map || parent in List )
        value = parent[key]
      else
        throw new MPLException("Invalid config key path '${key_list[i-1] = '<'+key_list[i-1]+'>'; key_list.join('.')}': marked key value type '${parent?.getClass()}' is not suitable to set the nested variable")

      if( value == null ) {
        parent[key] = [:]
        value = parent[key]
      }
    }

    parent[key] = val
  }

  /**
   * Easy way to debug the config content
   *
   * @return  String "MPLConfig(<config dump>)"
   */
  public String toString() {
    return "MPLConfig(${this.@config.dump()})"
  }
}
