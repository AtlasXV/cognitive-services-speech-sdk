import org.gradle.api.Project
import java.io.FileInputStream
import java.util.*

/**
 * weiping@atlasv.com
 * 2021/8/17
 */
fun Project.getCustomProperties(fileName: String): Properties? {
    val propertiesFile = rootProject.file(fileName)
    if (!propertiesFile.exists()) {
        return null
    }
    val properties = Properties()
    properties.load(FileInputStream(propertiesFile))
    return properties
}

val Project.subscriptionKey get() = findProperty("MICROSOFT_SPEECH_KEY")?.toString()!!
