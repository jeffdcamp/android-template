package org.jdc.template.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(layoutResourceId, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPostCreateView()
    }

    protected abstract val layoutResourceId: Int

    protected fun onPostCreateView() {
    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }
}
