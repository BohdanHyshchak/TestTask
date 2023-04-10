package com.youarelaunched.ui.screen.view

import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.data.repository.model.VendorCategory
import com.youarelaunched.challenge.ui.screen.view.VendorsVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class VendorsVMTest {

    // Subject under test
    private lateinit var vendorsVM: VendorsVM

    // Collaborators
    @Mock
    private lateinit var mockRepository: VendorsRepository

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // For the runTest{}
        Dispatchers.setMain(dispatcher)
        // Create the ViewModel with a mocked repository
        vendorsVM = VendorsVM(mockRepository)
    }

    @Test
    fun `getVendors - data loaded successfully with at least one item`() = runTest {
        // Given
        val categoriesList = listOf(
            VendorCategory(id = 1, title = "Category 1", imgUrl = "img_url_1"),
            VendorCategory(id = 2, title = "Category 2", imgUrl = "img_url_2")
        )

        val vendorsList = listOf(
            Vendor(
                id = 1,
                companyName = "Vendor 1",
                coverPhoto = "cover_photo_url",
                area = "Area 1",
                favorite = false,
                categories = categoriesList,
                tags = "tag1, tag2"
            )
        )
        `when`(mockRepository.getVendors()).thenReturn(vendorsList)

        // When
        vendorsVM.getVendors()

        // Wait for the function to complete
        delay(1000)

        // Then
        val uiState = vendorsVM.uiState.value
        assertThat(uiState.vendors, `is`(notNullValue()))
        assertThat(uiState.vendors!!.size, `is`(1))
        assertThat(uiState.vendors!![0].companyName, `is`("Vendor 1"))
    }

    @Test
    fun `getVendors - data loaded with error`() = runTest {
        // Given
        `when`(mockRepository.getVendors()).thenThrow(RuntimeException())

        // When
        vendorsVM.getVendors()

        // Wait for the function to complete
        delay(1000)

        // Then
        val uiState = vendorsVM.uiState.value
        assertThat(uiState.vendors, `is`(nullValue()))
    }
}
