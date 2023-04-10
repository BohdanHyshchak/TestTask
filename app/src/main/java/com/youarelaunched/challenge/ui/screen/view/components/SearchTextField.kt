package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.R
import com.youarelaunched.challenge.ui.screen.view.VendorsEvents
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun SearchTextField(
    searchInput: String,
    onEvent: (VendorsEvents) -> Unit
) {
    Surface(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        elevation = 4.dp,
    ) {
        OutlinedTextField(
            value = searchInput,
            onValueChange = {
                onEvent.invoke(VendorsEvents.EditSearchInput(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search...",
                    style = VendorAppTheme.typography.body2,
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                trailingIconColor = Color.Black,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.icon_search),
                    contentDescription = "Search button",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onEvent.invoke(VendorsEvents.OnSearchButtonClick) },
                )
            },
            textStyle = VendorAppTheme.typography.body1,
            singleLine = true
        )
    }
}
