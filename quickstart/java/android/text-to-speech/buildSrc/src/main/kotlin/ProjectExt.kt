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

/**
 * [根据文档获取语音资源的秘钥](https://learn.microsoft.com/zh-cn/azure/cognitive-services/speech-service/get-started-text-to-speech?tabs=macos%2Cterminal&pivots=programming-language-java#set-environment-variables)
 *
 * [个人主页可以查询](https://portal.azure.com/#home)
 **/
val Project.subscriptionKey get() = findProperty("MICROSOFT_SPEECH_KEY")?.toString()!!
