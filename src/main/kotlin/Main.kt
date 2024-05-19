import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.updatezipper.component.ApkFileTextField
import com.example.updatezipper.component.OutputDirectoryTextField


data class AppUiState(
    val output: String = "",
    val apk: String = "",
)

@Composable
internal fun MainScreen(state: MainState) {
    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
                .verticalScroll(state.scrollState)
        ) {
            val uiState = state.appUiState
            ApkFileTextField(
                path = uiState.apk,
                onValueChange = state::onApkPathChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.size(16.dp))

            OutputDirectoryTextField(
                path = uiState.output,
                onValueChange = state::onOutputChange,
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                state.startUpdateZip()
            }) {
                Text("実行")
            }
        }
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainScreen(rememberMainState())
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
