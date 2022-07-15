package com.wing.tree.bruni.daily.idioms.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.wing.tree.bruni.daily.idioms.R
import com.wing.tree.bruni.daily.idioms.enum.Category.*
import com.wing.tree.bruni.daily.idioms.ui.theme.DailyIdiomsTheme

class HomeFragment : Fragment() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                val items = listOf(
                    My to getString(R.string.my_idioms),
                    All to getString(R.string.all_idioms),
                    CivilServiceExamination to getString(R.string.civil_service_examination_idioms),
                    Sat to getString(R.string.sat_idioms),
                    Quiz to getString(R.string.quiz)
                )

                DailyIdiomsTheme {
                    Scaffold(
                        topBar = {

                        }
                    ) { paddingValues ->
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                            items(items) { (category, text) ->
                                Category(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp),
                                    category = category,
                                    text = text
                                ) {
                                    val directions = when(it) {
                                        // TODO quiz 는 다이얼로그 띄우도록 해야함. 갯수랑 카테고리 유저선택.
                                        Quiz -> HomeFragmentDirections
                                            .actionHomeFragmentToQuizFragment(Sat, 25)
                                        else -> HomeFragmentDirections
                                            .actionHomeFragmentToIdiomFragment(it)
                                    }

                                    findNavController().navigate(directions)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}