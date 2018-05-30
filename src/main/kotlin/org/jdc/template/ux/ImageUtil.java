package org.jdc.template.ux;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtil {

  @BindingAdapter("app:loadThumbnail")
  public static void loadThumbnail(ImageView view, String url) {
    Glide
        .with(view.getContext())
        .load(url)
        .thumbnail()
        .into(view);
  }
}
