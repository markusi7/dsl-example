package co.markusi.dsl.registrationform

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.widget.TextInputLayout
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout

@SuppressLint("ViewConstructor") // We don't want to use this View subclass in layouts
@RegistrationFormMarker
open class InputField(context: Context,
                      val minLength: Int,
                      val maxLength: Int,
                      val hint: String,
                      val errorMessage: String
) : TextInputLayout(context) {

    init {
        setHint(hint)
        counterMaxLength = maxLength

        val editText = EditText(context)
        editText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(editText)
    }

    open fun isValid(): Boolean {
        val text = editText!!.text
        val isNotBlank = text.isNotBlank()
        val validMinLength = text.length >= minLength
        val validMaxLength = maxLength < 0 || text.length < maxLength
        return isNotBlank && validMinLength && validMaxLength
    }

    private constructor(builder: Builder)
            : this(builder.context,
            builder.minLength,
            builder.maxLength,
            builder.hint,
            builder.errorMessage)

    fun showErrorIfInvalid() {
        if (!isValid()) {
            error = errorMessage
        }
    }

    fun hideError() {
        error = null
    }

    open class Builder protected constructor(val context: Context) {

        var minLength: Int = 0
        var maxLength: Int = Int.MAX_VALUE
        var hint: String = ""
        var errorMessage: String = ""

        constructor(context: Context, init: Builder.() -> Unit) : this(context) {
            init()
        }

        open fun build() = InputField(this)
    }
}