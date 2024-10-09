package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import ar.edu.unlam.mobile.scaffolding.domain.usecases.CaptureUserPhotoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureUserViewModel @Inject constructor(
    private val captureUserPhotoUseCase: CaptureUserPhotoUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow<CaptureUserPhotoUiState>(CaptureUserPhotoUiState.Starting)
    val state = _state.asStateFlow()

    fun onCapturedUserPhoto(picture: ByteArray?) {
        picture?.run {
            viewModelScope.launch {
                captureUserPhotoUseCase.savePhoto(UserPhoto(picture))
                _state.update { CaptureUserPhotoUiState.PhotoTaken(userPhoto = UserPhoto(picture)) }
            }
        }
    }

}

@Immutable
sealed interface CaptureUserPhotoUiState {
    data object Starting : CaptureUserPhotoUiState
    data class PhotoTaken(val userPhoto: UserPhoto) : CaptureUserPhotoUiState
    data class Error(val message: String) : CaptureUserPhotoUiState
}




