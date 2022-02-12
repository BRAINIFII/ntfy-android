package io.heckel.ntfy.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import io.heckel.ntfy.R

fun initBaseUrlDropdown(baseUrls: List<String>, textView: AutoCompleteTextView, layout: TextInputLayout) {
    // Base URL dropdown behavior; Oh my, why is this so complicated?!
    val toggleEndIcon = {
        if (textView.text.isNotEmpty()) {
            layout.setEndIconDrawable(R.drawable.ic_cancel_gray_24dp)
        } else if (baseUrls.isEmpty()) {
            layout.setEndIconDrawable(0)
        } else {
            layout.setEndIconDrawable(R.drawable.ic_drop_down_gray_24dp)
        }
    }
    layout.setEndIconOnClickListener {
        if (textView.text.isNotEmpty()) {
            textView.text.clear()
            if (baseUrls.isEmpty()) {
                layout.setEndIconDrawable(0)
            } else {
                layout.setEndIconDrawable(R.drawable.ic_drop_down_gray_24dp)
            }
        } else if (textView.text.isEmpty() && baseUrls.isNotEmpty()) {
            layout.setEndIconDrawable(R.drawable.ic_drop_up_gray_24dp)
            textView.showDropDown()
        }
    }
    textView.setOnDismissListener { toggleEndIcon() }
    textView.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            toggleEndIcon()
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Nothing
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Nothing
        }
    })

    val adapter = ArrayAdapter(textView.context, R.layout.fragment_add_dialog_dropdown_item, baseUrls)
    textView.threshold = 1
    textView.setAdapter(adapter)
    if (baseUrls.count() == 1) {
        layout.setEndIconDrawable(R.drawable.ic_cancel_gray_24dp)
        textView.setText(baseUrls.first())
    } else if (baseUrls.count() > 1) {
        layout.setEndIconDrawable(R.drawable.ic_drop_down_gray_24dp)
    } else {
        layout.setEndIconDrawable(0)
    }
}
