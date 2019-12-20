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

class CodeScanTest extends MPLTestBase {
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
                .codescan()
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
    void run_scan() throws Exception {
        script.call('SonarQube CodeScan')

        printCallStack()

        assertJobStatusSuccess()
    }

}
