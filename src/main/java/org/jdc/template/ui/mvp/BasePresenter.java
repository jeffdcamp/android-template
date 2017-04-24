package org.jdc.template.ui.mvp;

import javax.annotation.Nonnull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter {
    @Nonnull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    // use init() function to pass the VIEW and all other variables to initialize the presenter
    // fun init(view, id) { }

    /**
     * Usually called at the end of onCreate() to have the presenter start loading data into the view
     */
    public void load() {
    }

    /**
     * Usually called in onResume() to have the presenter reload data, if needed, into the view
     */
    public void reload(boolean forceRefresh) {
    }

    /**
     * Usually called in onStart() to have the presenter register event bus, listeners, observables, etc
     */
    public void register() {
    }

    /**
     * Usually called in onStop() to have the presenter unregister event bus, listeners, observables, etc
     */
    public void unregister() {
        compositeDisposable.dispose();
    }

    public void addDisposable(@Nonnull Disposable disposable) {
        if (compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
