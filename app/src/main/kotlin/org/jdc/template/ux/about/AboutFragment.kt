package org.jdc.template.ux.about

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.vikingsen.inject.viewmodel.savedstate.SavedStateViewModelFactory
import org.jdc.template.R
import org.jdc.template.databinding.AboutBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.BaseFragment
import javax.inject.Inject

class AboutFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactoryFactory: SavedStateViewModelFactory.Factory

    private val viewModel by viewModels<AboutViewModel> { viewModelFactoryFactory.create(this, null) }
    private lateinit var binding: AboutBinding

    init {
        Injector.get().inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.about, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = this@AboutFragment.viewModel
            lifecycleOwner = this@AboutFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.about_title)
        enableActionBarBackArrow(true)
        viewModel.logAnalytics()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.about_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_licenses -> {
                startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
