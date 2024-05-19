import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.oldguy.common.io.File
import com.oldguy.common.io.FileMode
import com.oldguy.common.io.ZipEntry
import com.oldguy.common.io.ZipFile
import java.nio.file.Files
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.io.path.Path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


internal interface MainState {
    fun onApkPathChange(s: String)
    fun onOutputChange(s: String)
    fun startUpdateZip()

    val scrollState: ScrollState
    val appUiState: AppUiState
    val scope: CoroutineScope
}

private class MainStateImpl(override val scrollState: ScrollState, override val scope: CoroutineScope) : MainState {
    override var appUiState: AppUiState by mutableStateOf(AppUiState())

    override fun onApkPathChange(s: String) {
        appUiState = appUiState.copy(apk = s)
    }

    override fun onOutputChange(s: String) {
        appUiState = appUiState.copy(output = s)
    }

    override fun startUpdateZip() {
        val dir = File(appUiState.output)
        val zip = File(dir, "apk_update.zip")
        val apkMd5 = md5sum(appUiState.apk)
        val apkFile = File(appUiState.apk)
        println("apk-md5=$apkMd5")
        scope.launch(Dispatchers.IO) {
            zip.delete()
            ZipFile(zip, FileMode.Write).use {
                it.zipFile(apkFile)
                ZipEntry(
                    nameArg = "someData.dat"
                )
            }
        }
    }

    private fun md5sum(path: String): String {
        val digest = MessageDigest.getInstance("MD5")
        DigestInputStream(Files.newInputStream(Path(path)), digest).use { input ->
            while (input.read() != -1) {
                // PASS;
            }
            val hash = StringBuilder()
            for (b in digest.digest()) {
                hash.append(String.format("%02x", b))
            }
            return hash.toString()
        }
    }
}

@Composable
internal fun rememberMainState(): MainState {
    return MainStateImpl(scrollState = rememberScrollState(), scope = rememberCoroutineScope())
}
