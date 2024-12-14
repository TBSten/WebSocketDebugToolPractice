import dsl.alias
import dsl.ktlint
import dsl.libs
import dsl.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

open class LintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                alias(libs.plugin("ktlint"))
            }
            ktlint {
                filter {
                    exclude("**/generated/**")
                }
            }
        }
    }
}
