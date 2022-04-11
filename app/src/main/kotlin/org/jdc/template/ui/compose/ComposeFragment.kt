package org.jdc.template.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.jdc.template.ui.theme.AppTheme

/**
 * Simplify the Fragments till fragments are removed in favor of NavigationCompose
 */
abstract class ComposeFragment : Fragment() {

    @Composable
    abstract fun ComposeScreen(navController: NavController)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ComposeScreen(findNavController())
                }
            }
        }
    }
}