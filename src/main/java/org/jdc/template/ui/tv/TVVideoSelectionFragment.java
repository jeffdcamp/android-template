package org.jdc.template.ui.tv;


import android.annotation.TargetApi;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.database.CursorMapper;
import android.support.v17.leanback.widget.BrowseFrameLayout;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.TitleView;
import android.support.v17.leanback.widget.VerticalGridView;
import android.util.Log;
import android.view.Display;
import android.view.View;

import org.jdc.template.App;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;

import java.lang.reflect.Field;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TVVideoSelectionFragment extends BrowseFragment {
    private static final String TAG = App.createTag(TVVideoSelectionFragment.class);

//    private BackgroundManagerTarget backgroundManagerTarget = new BackgroundManagerTarget();
    private BackgroundManager backgroundManager;
    private Handler handler = new Handler();

    private Point displaySize = new Point();
    private String currentBackgroundUrl;

    @Inject
    EventBus bus;

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);

        if (currentBackgroundUrl != null) {
            setImageBackgroundDelayed(currentBackgroundUrl, 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Injector.get().inject(this);

        initializeBackgroundManager();
        setupUIElements();
        setupEventListeners();
        loadContent();
    }


    private void initializeBackgroundManager() {
        backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        backgroundManager.setColor(getResources().getColor(R.color.app_theme_color));
    }

    private void setupUIElements() {
        setTitle(getString(R.string.app_name));
        setBadgeDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        setHeadersState(HEADERS_DISABLED);
        enableSearchSelectionWorkaround();
        setHeadersTransitionOnBackEnabled(true);

        setBrandColor(getResources().getColor(R.color.default_text));
        setSearchAffordanceColor(getResources().getColor(R.color.default_background));

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(displaySize);
    }

    private void loadContent() {
//        Cursor groupCursor = groupManager.findCursorAllChronologicalOrder();
//        if (groupCursor == null) {
//            return;
//        }
//
//        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
//        CardPresenter presenter = new CardPresenter(getActivity());
//
//        CursorObjectAdapter cursorAdapter = new CursorObjectAdapter(presenter);
//        cursorAdapter.setMapper(new VideoGroupCursorMapper());
//        cursorAdapter.changeCursor(groupCursor);
//
//        String headerText = ""; // getString(R.string.nav_title_watch)
//        HeaderItem header = new HeaderItem(0, headerText, null);
//        rowsAdapter.add(new ListRow(header, cursorAdapter));
//
//        setAdapter(rowsAdapter);
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(new SearchClickedListener());
        setOnItemViewSelectedListener(new VideoSelectedListener());
        setOnItemViewClickedListener(new VideoClickedListener());
    }

    private void setImageBackgroundDelayed(String url, int delay) {
        //If we are displaying the default background, then update immediately
//        if (currentBackgroundUrl == null) {
//            delay = 0;
//        }
//
//        if (url != null) {
//            currentBackgroundUrl = url;
//        }
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Picasso.with(getActivity())
//                        .load(currentBackgroundUrl)
//                        .resize(displaySize.x, displaySize.y)
//                        .centerCrop()
//                        .into(backgroundManagerTarget);
//            }
//        }, delay);
    }

    private class VideoSelectedListener implements OnItemViewSelectedListener {
        private static final int BACKGROUND_DELAY = 300;

        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
//            if (item instanceof VideoGroup) {
//                setImageBackgroundDelayed(((VideoGroup) item).getVideoStillUrl(), BACKGROUND_DELAY);
//            }
        }
    }

    /**
     * Due to a bug in the BrowseFragment source, we are unable to
     * navigate away from the search orb once it is selected.
     * <p/>
     * TODO: once the linked bug is fixed and released in OTA then remove this workaround
     * <p/>
     * https://code.google.com/p/android/issues/detail?id=81718
     */
    private void enableSearchSelectionWorkaround() {
        BrowseFrameLayout browseFrame = (BrowseFrameLayout) getPrivateField(BrowseFragment.class, this, "mBrowseFrame");
        if (browseFrame != null) {
            browseFrame.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() {
                @Override
                public View onFocusSearch(View focused, int direction) {
                    TitleView titleView = (TitleView) getPrivateField(BrowseFragment.class, TVVideoSelectionFragment.this, "mTitleView");
                    View searchOrbView = titleView.getSearchAffordanceView();

                    if (focused.equals(searchOrbView) && direction == View.FOCUS_DOWN) {
                        Object rowsFragment = getPrivateField(BrowseFragment.class, TVVideoSelectionFragment.this, "mRowsFragment");
                        Class<?> clazz = getClassForName("android.support.v17.leanback.app.BaseRowFragment");
                        return (VerticalGridView) getPrivateField(clazz, rowsFragment, "mVerticalGridView");
                    } else if (!focused.equals(searchOrbView) && searchOrbView.getVisibility() == View.VISIBLE && direction == View.FOCUS_UP) {
                        return searchOrbView;
                    }

                    return null;
                }
            });
        }
    }

    private Object getPrivateField(Class clazz, Object parent, String fieldName) {
        try {
            Field privateBrowseFrame = clazz.getDeclaredField(fieldName);
            privateBrowseFrame.setAccessible(true);

            return privateBrowseFrame.get(parent);
        } catch (Exception e) {
            Log.e(TAG, "can't get private field " + fieldName, e);
        }

        return null;
    }

    private Class<?> getClassForName(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            Log.e(TAG, "unable to get class for " + name, e);
        }

        return null;
    }

    private class VideoClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
//            if (item instanceof VideoGroup) {
//                internalIntents.startTVVideoPlayerActivity(getActivity(), ((VideoGroup) item).getId());
//            }
        }
    }

    private class SearchClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            internalIntents.startTVSearchActivity(getActivity());
        }
    }

    private static class VideoGroupCursorMapper extends CursorMapper {
        @Override
        protected void bindColumns(Cursor cursor) {
            //Purposefully left blank
        }

        @Override
        protected Object bind(Cursor cursor) {

//            return new VideoGroup(cursor);
            return null;
        }
    }

//    private class BackgroundManagerTarget implements Target {
//        @Override
//        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//            if (backgroundManager != null) {
//                backgroundManager.setBitmap(bitmap);
//            }
//        }
//
//        @Override
//        public void onBitmapFailed(Drawable errorDrawable) {
//            //Purposefully left blank
//        }
//
//        @Override
//        public void onPrepareLoad(Drawable placeHolderDrawable) {
//            //Purposefully left blank
//        }
//    }
}
