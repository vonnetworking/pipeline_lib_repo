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

import org.junit.Before
import org.junit.Test

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.LocalSource.localSource

import static org.assertj.core.api.Assertions.assertThat

import com.fhlmc.moderndelivery.mpl.Helper
import com.fhlmc.moderndelivery.mpl.testing.MPLTestBase

class BuildTest extends MPLTestBase {
  def script = null

  @Override
  @Before
  void setUp() throws Exception {
    String sharedLibs = this.class.getResource('.').getFile()

    helper.registerSharedLibrary(library()
        .name('mpl')
        .allowOverride(false)
        .retriever(localSource(sharedLibs))
        .targetPath(sharedLibs)
        .defaultVersion('snapshot')
        .implicit(true)
        .build()
    )

    setScriptRoots([ 'vars' ] as String[])
    setScriptExtension('groovy')

    super.setUp()

    binding.setVariable('env', [:])

    helper.registerAllowedMethod('fileExists', [String.class], { true })
    helper.registerAllowedMethod('tool', [String.class], { name -> "${name}_HOME" })
    helper.registerAllowedMethod('withEnv', [List.class, Closure.class], null)

    script = loadScript('MPLModule.groovy')
  }

  @Test
  void default_run() throws Exception {
    script.call('Build')

    printCallStack()

    assertThat(helper.callStack)
      .filteredOn { c -> c.methodName == 'tool' }
      .filteredOn { c -> c.argsToString().contains('Maven 3') }
      .isNotEmpty()

    assertThat(helper.callStack)
      .as('Shell execution should contain mvn command and default clean install')
      .filteredOn { c -> c.methodName == 'sh' }
      .filteredOn { c -> c.argsToString().startsWith('mvn') }
      .filteredOn { c -> c.argsToString().contains('clean install') }
      .isNotEmpty()

    assertThat(helper.callStack)
      .as('Default mvn run without settings provided')
      .filteredOn { c -> c.methodName == 'sh' }
      .filteredOn { c -> c.argsToString().startsWith('mvn') }
      .filteredOn { c -> ! c.argsToString().contains('-s ') }
      .isNotEmpty()

    assertJobStatusSuccess()
  }

  @Test
  void change_tool() throws Exception {
    script.call('Build', [
      maven: [
        tool_version: 'Maven 2',
      ],
    ])

    printCallStack()

    assertThat(helper.callStack)
      .as('Changing maven tool name')
      .filteredOn { c -> c.methodName == 'tool' }
      .filteredOn { c -> c.argsToString().contains('Maven 2') }
      .isNotEmpty()

    assertJobStatusSuccess()
  }

  @Test
  void change_settings() throws Exception {
    script.call('Build', [
      maven: [
        settings_path: '/test-settings.xml',
      ],
    ])

    printCallStack()

    assertThat(helper.callStack)
      .as('Providing setings file should set the maven opetion')
      .filteredOn { c -> c.methodName == 'sh' }
      .filteredOn { c -> c.argsToString().contains("-s '/test-settings.xml'") }
      .isNotEmpty()

    assertJobStatusSuccess()
  }
}
