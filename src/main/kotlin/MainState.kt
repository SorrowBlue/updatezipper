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
        val apkFile = File(appUiState.apk)
        scope.launch(Dispatchers.IO) {
            ZipFile(zip, FileMode.Write).use {
                it.zipFile(apkFile)
                ZipEntry(
                    nameArg = "someData.dat"
                )
            }
        }
    }
}

@Composable
internal fun rememberMainState(): MainState {
    return MainStateImpl(scrollState = rememberScrollState(), scope = rememberCoroutineScope())
}
