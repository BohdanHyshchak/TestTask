package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.youarelaunched.challenge.R
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun NoResultItem(modifier: Modifier) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.msg_no_result_title),
            color = VendorAppTheme.colors.textError,
            style = VendorAppTheme.typography.h2,
        )
        Text(
            text = stringResource(id = R.string.msg_no_result_subtitle),
            color = VendorAppTheme.colors.textDark,
            style = VendorAppTheme.typography.body2,
            textAlign = TextAlign.Center,
        )

    }
}