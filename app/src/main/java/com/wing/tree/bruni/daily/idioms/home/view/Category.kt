package com.wing.tree.bruni.daily.idioms.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wing.tree.bruni.daily.idioms.enum.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Category(
    modifier: Modifier,
    category: Category,
    text: String,
    onClick: (Category) -> Unit
) {
    Card(modifier = modifier.clickable(onClick = { onClick(category) })) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text)
        }
    }
}