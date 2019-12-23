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

class HealthCheckTest extends MPLTestBase {
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

        script = loadScript('MPLModule.groovy')
    }
    @Test
    void checkThatHealthCheckSucceedsWithOkCode() throws Exception {

        helper.registerAllowedMethod('getResponseCode', [String.class], { "200" })
        script.call('HealthCheck')

        printCallStack()
        assertJobStatusSuccess()
    }


}
