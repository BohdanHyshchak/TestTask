package com.youarelaunched.challenge.ui.screen.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.VendorsEvents.EditSearchInput
import com.youarelaunched.challenge.ui.screen.view.VendorsEvents.OnSearchButtonClick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_DEBOUNCE_MS = 500L
private const val MIN_VALID_INPUT = 3

@OptIn(FlowPreview::class)
@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        VendorsScreenUiState(
            vendors = null,
            searchInput = "",
        ),
    )
    val uiState = _uiState.asStateFlow()

    init {
        getVendors()

        // In this project we share already uiState, so we don't have possibility to work with
        // vendors and searchInput separately --> I decided to put debounce search logic in init block
        // to avoid creation of additional flows
        viewModelScope.launch {
            viewModelScope.launch {
                uiState
                    .debounce(SEARCH_DEBOUNCE_MS)
                    .map { it.searchInput }
                    .distinctUntilChanged()
                    .filter { it.length >= MIN_VALID_INPUT }
                    .collect {
                        getVendors(it)
                    }
            }
        }
    }

    // Added query: String? parameter to allow filter list with this query
    fun getVendors(query: String? = null) {
        viewModelScope.launch {
            val vendorsList = repository.getVendors()

            _uiState.update { state ->
                if (!query.isNullOrBlank()) { // if we have valid query
                    state.copy(
                        vendors = vendorsList.filter { vendor ->
                            vendor.companyName.contains(query, ignoreCase = true)
                        }
                    )
                } else
                    state.copy(vendors = vendorsList)
            }
        }
    }

    fun obtainEvent(event: VendorsEvents) = when (event) {
        is EditSearchInput -> _uiState.update {
            it.copy(
                searchInput = event.input,
            )
        }
        is OnSearchButtonClick -> {
            getVendors(_uiState.value.searchInput)
        }
    }
}

sealed interface VendorsEvents {
    @JvmInline
    value class EditSearchInput(val input: String) : VendorsEvents
    object OnSearchButtonClick : VendorsEvents
}
