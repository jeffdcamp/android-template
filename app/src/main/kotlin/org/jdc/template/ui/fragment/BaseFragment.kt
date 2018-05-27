package org.jdc.template.ui.fragment

abstract class BaseFragment : LiveDataObserverFragment() {
    fun enableActionBarBackArrow(enable: Boolean) {
        activity?.actionBar?.apply {
            setHomeButtonEnabled(enable)
            setDisplayHomeAsUpEnabled(enable)
        }
    }
}