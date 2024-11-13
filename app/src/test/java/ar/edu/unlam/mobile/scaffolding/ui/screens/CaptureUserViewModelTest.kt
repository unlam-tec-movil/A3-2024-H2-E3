package ar.edu.unlam.mobile.scaffolding.ui.screens

import ar.edu.unlam.mobile.scaffolding.domain.models.UserPhoto
import ar.edu.unlam.mobile.scaffolding.domain.models.errors.CaptureUserPhotoErrors
import ar.edu.unlam.mobile.scaffolding.domain.usecases.CaptureUserPhotoUseCases
import ar.edu.unlam.mobile.scaffolding.fakes.CaptureUserPhotoUseCasesFake
import ar.edu.unlam.mobile.scaffolding.ui.screens.captureuserphoto.CaptureUserPhotoUiState
import ar.edu.unlam.mobile.scaffolding.ui.screens.captureuserphoto.CaptureUserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rules.TestCoroutineRule

@OptIn(ExperimentalCoroutinesApi::class)
class CaptureUserViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var viewModel: CaptureUserViewModel
    private lateinit var captureUserPhotoUseCase: CaptureUserPhotoUseCases

    @Before
    fun setUp() {
        captureUserPhotoUseCase = CaptureUserPhotoUseCasesFake()
        viewModel = CaptureUserViewModel(captureUserPhotoUseCase = captureUserPhotoUseCase)
    }

    @Test
    fun `when created state should be starting`() {
        assertEquals(CaptureUserPhotoUiState.Starting, viewModel.state.value)
    }

    @Test
    fun `when user photo is captured and is not null the photo should be saved`() =
        runTest {
            viewModel.onCapturedUserPhoto(byteArrayOf(1, 2, 3))
            advanceUntilIdle()
            assertEquals(UserPhoto(byteArrayOf(1, 2, 3)), captureUserPhotoUseCase.getPhoto())
        }

    @Test
    fun `when user photo is captured and is null the photo shouldn't be saved`() =
        runTest {
            viewModel.onCapturedUserPhoto(null)
            advanceUntilIdle()
            assertNull(captureUserPhotoUseCase.getPhoto())
        }

    @Test
    fun `when user photo is correctly saved state should be photo taken`() =
        runTest {
            viewModel.onCapturedUserPhoto(byteArrayOf(1, 2, 3))
            advanceUntilIdle()
            assertEquals(
                CaptureUserPhotoUiState.PhotoTaken(UserPhoto(byteArrayOf(1, 2, 3))),
                viewModel.state.value,
            )
        }

    @Test
    fun `when error happened while saving the photo state should be error`() =
        runTest {
            viewModel.onCapturedUserPhoto(byteArrayOf())
            advanceUntilIdle()
            assertEquals(
                CaptureUserPhotoUiState.Error(CaptureUserPhotoErrors.SavePictureError("Error while saving the picture")),
                viewModel.state.value,
            )
        }
}
