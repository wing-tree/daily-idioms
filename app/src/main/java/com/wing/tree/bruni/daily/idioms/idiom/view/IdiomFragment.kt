package com.wing.tree.bruni.daily.idioms.idiom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wing.tree.bruni.daily.idioms.idiom.viewmodel.IdiomViewModel
import com.wing.tree.bruni.daily.idioms.popBackStack
import com.wing.tree.bruni.daily.idioms.ui.theme.DailyIdiomsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdiomFragment : Fragment() {
    private val viewModel by viewModels<IdiomViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                val state by viewModel.state.collectAsState()

                DailyIdiomsTheme {
                    Surface {
                        Scaffold(
                            topBar = {
                                TopBar(
                                    state = state,
                                    onBackPressed = { popBackStack() }
                                )
                            }
                        ) { paddingValues ->
                            Idioms(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues),
                                state = state,
                                onIconButtonClick = { idiom ->
                                    viewModel.updateMy(idiom)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}