package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.components.ChatsumerSnackbar
import com.youarelaunched.challenge.ui.screen.view.components.NoResultItem
import com.youarelaunched.challenge.ui.screen.view.components.SearchTextField
import com.youarelaunched.challenge.ui.screen.view.components.VendorItem
import com.youarelaunched.challenge.ui.theme.VendorAppTheme


@Composable
fun VendorsRoute(
    viewModel: VendorsVM,
) {
    val uiState by viewModel.uiState.collectAsState()
    VendorsScreen(uiState = uiState, viewModel::obtainEvent)
}

@Composable
fun VendorsScreen(
    uiState: VendorsScreenUiState,
    onEvent: (VendorsEvents) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = VendorAppTheme.colors.background,
        snackbarHost = { ChatsumerSnackbar(it) },
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchTextField(uiState.searchInput, onEvent)
            if (!uiState.vendors.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddings)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        vertical = 24.dp,
                        horizontal = 16.dp,
                    ),
                ) {
                    items(uiState.vendors) { vendor ->
                        VendorItem(
                            vendor = vendor,
                        )
                    }
                }
            } else {
                NoResultItem(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}
