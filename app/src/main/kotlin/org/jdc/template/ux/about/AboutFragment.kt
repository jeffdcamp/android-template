package org.jdc.template.ux.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.databinding.AboutFragmentBinding
import org.jdc.template.ext.collectWhenStarted

@AndroidEntryPoint
class AboutFragment : Fragment() {
    private val viewModel: AboutViewModel by viewModels()

    private var _binding: AboutFragmentBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = AboutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = this@AboutFragment.viewModel
        binding.lifecycleOwner = this@AboutFragment

        activity?.setTitle(R.string.about_title)
        viewModel.logAnalytics()

        viewLifecycleOwner.collectWhenStarted(viewModel.resetServiceEnabledFlow) {
            binding.restServiceEnabledTextView.text = it.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.about_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_licenses -> {
                findNavController().navigate(AboutFragmentDirections.actionToAcknowledgmentsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
