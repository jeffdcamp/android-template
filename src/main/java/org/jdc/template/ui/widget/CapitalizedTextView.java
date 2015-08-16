package org.jdc.template.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Support for CAPITALIZED headers for both Android 2.x and Android 4.0+
 * Helps remove the need to have ALL CAPS text in strings.xml
 * Remove this class and replace with textAllCaps=true when Android 2.3.x support is dropped
 */
public class CapitalizedTextView extends TextView {

    public CapitalizedTextView(Context context) {
        super(context);
    }

    public CapitalizedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CapitalizedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text.toString().toUpperCase(), type);
    }
}