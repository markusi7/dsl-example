package co.markusi.dsl.registrationform

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import co.markusi.dsl.R

@RegistrationFormMarker
@SuppressLint("ViewConstructor") // We don't want to use this View subclass in layouts
class FormCheckBox private constructor(
        context: Context,
        val text: String,
        val isMandatory: Boolean,
        var isChecked: Boolean)
    : LinearLayout(context) {

    private var errorTextView: TextView? = null

    private constructor(builder: Builder) : this(builder.context,
            builder.text,
            builder.isMandatory,
            builder.isChecked) {
        orientation = VERTICAL
        val checkBox = CheckBox(context)
        checkBox.apply {
            text = builder.text
            isChecked = builder.isChecked
            setOnCheckedChangeListener { _, isChecked -> this@FormCheckBox.isChecked = isChecked }
        }
        addView(checkBox)

        if (isMandatory) {
            errorTextView = TextView(builder.context)
            errorTextView!!.apply {
                setTextColor(ContextCompat.getColor(builder.context, android.R.color.holo_red_light))
                setText(R.string.this_checkbox_must_be_checked)
                visibility = View.INVISIBLE
            }
            addView(errorTextView)

                listOf('1', '2','3').filter {
                    it.toInt() /2 == 0
                }
        }
    }

    class Builder private constructor(val context: Context) {
        var text = ""
        var isChecked: Boolean = false
        var isMandatory: Boolean = false

        constructor(context: Context, init: Builder.() -> Unit) : this(context) {
            init()
        }

        fun build() = FormCheckBox(this)
    }

    fun showErrorIfInvalid() {
        if (!isValid()) {
            errorTextView?.visibility = View.VISIBLE
        }
    }

    fun hideError() {
        errorTextView?.visibility = View.INVISIBLE
    }

    fun isValid(): Boolean = !isMandatory || isChecked
}