package ru.ltst.u2020mvp.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

public class DrawableUtils {

    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(drawableRes, context.getTheme());
        } else {
            return context.getResources().getDrawable(drawableRes);
        }
    }
}
