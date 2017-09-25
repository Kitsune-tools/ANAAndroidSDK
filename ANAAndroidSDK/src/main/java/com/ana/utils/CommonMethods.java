package com.ana.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ana.R;
import com.ana.ui.ChatWebViewActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Admin on 29-06-2017.
 */

public class CommonMethods {

    public static void hideSoftKeyboard(Context context,View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public static String fromHtml(String s){
        Spanned spannable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spannable = Html.fromHtml(s,Html.FROM_HTML_MODE_COMPACT);
        }else{
            spannable = Html.fromHtml(s);
        }
        return spannable.toString();

    }
    public static void linkClickableText(final Context mContext, SpannableStringBuilder sp, CharSequence charSequence) {

        URLSpan[] spans = sp.getSpans(0, charSequence.length(), URLSpan.class);

        for (final URLSpan urlSpan : spans) {

            ClickableSpan clickableSpan = new ClickableSpan() {
                public void onClick(View view) {
                    Log.e("urlSpan", urlSpan.getURL());

                    Intent intent = new Intent(mContext, ChatWebViewActivity.class);
                    intent.putExtra(ChatWebViewActivity.KEY_URL, urlSpan.getURL());
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            };

            sp.setSpan(clickableSpan, sp.getSpanStart(urlSpan),
                    sp.getSpanEnd(urlSpan), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }

    }
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(date);
    }

    public static String getMapUrlFromLocation(String lattitude, String longitude){

        String url =  "http://maps.google.com/maps/api/staticmap?center=" + lattitude + ","
                + longitude + "&zoom=19&size=1000x300&sensor=false" + "&key="
                + Constants.MAP_KEY;
        Log.d("LatLong", url);
        return url;
    }
}
