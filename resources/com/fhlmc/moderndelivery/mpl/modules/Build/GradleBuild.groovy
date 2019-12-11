/**
 * Gradle Build
 */

withEnv(["PATH+GRADLE=${tool(CFG.'gradle.tool_version' ?: 'Gradle 1.33')}/bin"]) {
  def settings = CFG.'gradle.settings_path' ? "-s '${CFG.'gradle.settings_path'}'" : ''
  sh """gradle build"""
}
