package co.markusi.dsl.registrationform

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

@RegistrationFormMarker
@SuppressLint("ViewConstructor") // We don't want to use this View subclass in layouts
class DropdownMenu(context: Context) : LinearLayout(context) {

    private constructor(builder: Builder) : this(builder.context) {
        if (!builder.items.contains(builder.selectedItem)) {
            throw RegistrationFormSetupException()
        }

        orientation = HORIZONTAL
        val textView = TextView(builder.context)
        textView.layoutParams =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.text = builder.text
        val spinner = Spinner(builder.context)
        val adapter = ArrayAdapter(builder.context, android.R.layout.simple_spinner_item, builder.items.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val dotTextView = TextView(builder.context)
        textView.layoutParams =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dotTextView.text = "."

        addView(textView)
        addView(spinner)
        addView(dotTextView)

    }

    class Builder private constructor(val context: Context) {

        var items: List<String> = emptyList()
        var selectedItem: String = ""
        var text = ""

        constructor(context: Context, init: Builder.() -> Unit)
                : this(context) {
            init()
        }

        fun build() = DropdownMenu(this)
    }
}