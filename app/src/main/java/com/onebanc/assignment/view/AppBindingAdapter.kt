package com.onebanc.assignment.view

import android.text.SpannableString
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

object AppBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(textView: TextView, spannableString: SpannableString?) {
        textView.text = spannableString
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(textView: TextView, @StringRes stringResId: Int?) {
        textView.text = if (stringResId == null || stringResId == 0) {
            null
        } else {
            textView.context.getString(stringResId)
        }
    }
}