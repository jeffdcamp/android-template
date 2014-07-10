package org.company.project.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import org.company.project.R;

public class CheckableLinearLayout extends LinearLayout implements Checkable {
    private boolean checked;

    public CheckableLinearLayout(Context context) {
        super(context);
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        setBackgroundResource(checked ? R.color.list_selected_item_background : 0);
        View notSelectedView = findViewById(R.id.listview_sidebar);
        if (notSelectedView != null) {
            notSelectedView.setVisibility(checked ? View.GONE : View.VISIBLE);
        }

        View selectedView = findViewById(R.id.listview_sidebar_selected);
        if (selectedView != null) {
            selectedView.setVisibility(checked ? View.VISIBLE : View.GONE);
        }

        View selectedHeaderView = findViewById(R.id.listview_header_sidebar);
        if (selectedHeaderView != null) {
            selectedHeaderView.setVisibility(View.VISIBLE);
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void toggle() {
        setChecked(!checked);
    }
}
