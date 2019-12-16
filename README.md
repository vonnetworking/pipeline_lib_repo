Modern Delivery Modular Pipeline Library
========================================
Shared jenkins library with modular structure allow to write a simple pipeline modules, test it properly and use in any kind of pipelines.

## Goals


* Provide core to support flexible modular structure
* Enable support for declarative & scripted pipelines
* Ensure that nested libraries could reuse the MPL features
* Allow module overrides from the project or nested libraries
* Prepare set of simple & minimal basic pipelines
* Make it easy to create tests for modules & pipelines

## Documentation

This readme contains mostly technical information, for more details, please check the resources in Confluence:

https://confluence.agiletrailblazers.com/display/FMT/DevOps+Pipeline+2.0

## Dependencies

* Jenkins >= 2.74 LTS
* workflow-cps >= 2.44
* workflow-job >= 2.15
* workflow-cps-global-lib >= 2.8

## Setup

Go to: Manage Jenkins --> Configure System --> Global Pipeline Libraries:

* Name: `mpl`
  * Default Version: `<empty>`
  * Load implicitly: `false`
  * Include @Library changes in job recent changes: `true`
  * Retrieval method: `Modern SCM`
  * Source Code Management: `Git`
  * Project Repository: `https://github.com/agiletrailblazers/mdmpl.git`
  * Behaviors: `Discover branches`, `Discover tags`

## Usage

Applications use MPL as a library reference in the application's Jenkinsfile:

* **Modules**  - Common steps that will be found in each pipeline. Prepares the pipeline structure and use the required modules.

### Jenkinsfile / Pipeline script

The pipeline needs to add the following code in the application project Jenkinsfile or in the Jenkins Pipeline Script:
```
@Library('mpl') _
MPLPipeline {}
```

### Configuration

Usually configuration is initialized in the pipeline - it's calling `MPLPipelineConfig` interface with arguments:
* `body` - Map/Closure with configuration from the Jenkinsfile
* `defaults` - pipeline default values (could be overridden by Jenkinsfile)
* `overrides` - pipeline hard values (could not be overridden by Jenkinsfile)

After that pipeline defining MPL object and use it's common functions to define the pipeline itself. Pipeline is calling MPLModule that calling the required module logic.

In the module we have a common predefined variables (like default `steps`, `env`, `params`, `currentBuild`...) and a new variable contains the pipeline/module configs: `CFG`.
It's a special MPLConfig object that defines interface to get and set the properties. It's promising a number of things:
* Get value will never throw exception
* Unable to change values of `CFG` through get or clone
* It's not related to the parent module or pipeline `CFG` - any changes could be only forwarded to the children module
* You can get raw config Map/List - but they will act like a normal Map/List (could cause exceptions)
* Set of the `CFG` value could cause exceptions in certain circumstances:
  * set improper List index (non-positive integer)
  * set sublevels for defined non-collections

Use of the `CFG` object is quite simple. Imagine we have the next pipeline configuration:
```
[
  agent_label: '',
  val1: 4,
  val2: [
    val_nested: 'value',
    val_list: [1,2,3,4],
  ],
]
```

* Get value of specific property:
  * `CFG.val1` == `CFG.'val1'` == `4`
  * `CFG.'val2.val_nested'` == `'value'`
  * `CFG.'val2.not_exist'` == `null`
  * `CFG.'val2.not_exist' ?: 'default'` == `'default'`
  * `CFG.'val2.val_list.2'` == `3`
* Get raw Map or List:
  * `CFG.'val2.val_list'` == `[1,2,3,4]`
  * `CFG.val2` == `[val_nested:'value',val_list:[1,2,3,4]]`

* Set value of a specific property:
  * `CFG.val1 = 3`; `CFG.val1` == `3`
  * `CFG.'val2.val_nested' = 555`; `CFG.val2.val_nested` == `555`
* Create new properties:
  * `CFG.val4 = 'newval'`; `CFG.val4` == `'newval'`
  * `CFG.'val2.val_list.5' = 333`; `CFG.'val2.val_list'` == `[1,2,3,4,null,333]`
* Replace entire Map or List:
  * `CFG.'val2.val_list' = null`; `CFG.val2` == `[val_nested:'value',val_list:null]`
  * `CFG.val2 = [new_key:[4,3,2,1]]`; `CFG.val2` == `[new_key:[4,3,2,1]]`

The above can be used to create more interfaces and modules.

### Modules

MPL is mostly modules with logic. Basic features:

* Simple groovy sandbox step files with all the pipeline features
* Could be used in declarative & scripted pipelines
* Override system with execution protection

Modules are loaded from the MPL library:
* `{Library}/resources/com/fhlmc/devops/mpl/modules/{Stage}/{Name}{Stage}.groovy` - library modules for everyone

Check the usage example below

### Standard Pipeline usage

Typical standard pipeline usage with application specific overrides.

`{ProjectRepo}/Jenkinsfile`:
```
@Library('mpl') _

// Use default master pipeline
MPLPipeline {
  // Pipeline configuration override here
  // Example: (check available options in the pipeline definition)
  agent_label = 'LS'                     // Set agent label
  modules.Build.tool_version = 'Maven 2' // Change tool for build stage
  modules.CodeScan.profile = 'exempt'    // Uses the 'exempt' quality profile
  modules.CodeScan.scan_type = 'java'   // specifies the type of applicaction that will be scanned
}
```

### Handling illegal arguments in MPL closure

Attempts to override the default stages and accepted parameter values will fail the pipeline build


