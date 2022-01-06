package org.jdc.template.ux.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.menu.CommonMenu
import org.jdc.template.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class DirectoryFragment : Fragment() {
    @Inject
    lateinit var commonMenu: CommonMenu

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CompositionLocalProvider(LocalNavController provides findNavController()) {
                    AppTheme {
                        Surface {
                            DirectoryScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.directory_menu, menu)
        inflater.inflate(R.menu.common_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return commonMenu.onOptionsItemSelected(findNavController(), item) || super.onOptionsItemSelected(item)
    }
}

