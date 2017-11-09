package co.markusi.dsl.registrationform

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import co.markusi.dsl.R


@SuppressLint("ViewConstructor") // We don't want to use this View subclass in layouts
class RegistrationForm private constructor(context: Context,
                                           val registerButton: RegisterButton)
    : LinearLayout(context) {

    private constructor(builder: Builder) : this(
            builder.context,
            builder.registerButton) {
        orientation = VERTICAL
        val formPadding = context.resources.getDimensionPixelOffset(R.dimen.spacing_2x)
        setPadding(formPadding, formPadding, formPadding, formPadding)
        builder.checkBoxes.forEach {
            addView(it)
        }
        addView(registerButton)
    }

    companion object {
        fun create(context: Context, init: Builder.() -> Unit) = Builder(context, init).build()
    }

    fun isValid(): Boolean {
        return true
    }

    class Builder private constructor(val context: Context) {

        lateinit var usernameField: InputField
        lateinit var passwordField: PasswordInputField
        lateinit var dropdownMenu: DropdownMenu
        val checkBoxes: MutableList<FormCheckBox> = mutableListOf()
        lateinit var registerButton: RegisterButton

        private val defaultPadding = context.resources.getDimensionPixelOffset(R.dimen.spacing_1x)

        constructor(context: Context, init: Builder.() -> Unit) : this(context) {
            init()
        }

        fun build() = RegistrationForm(this)

        fun registerButton(init: RegisterButton.Builder.() -> Unit) {
            registerButton = RegisterButton.Builder(context, init).build()
            registerButton.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        }

        private fun applyDefaultFieldAttributes(view: View) {
            view.apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                minimumHeight = context.resources.getDimensionPixelOffset(R.dimen.input_field_height)
                setPadding(0, defaultPadding, 0, defaultPadding)
            }
        }
    }

    fun hideErrors() {

    }

    fun showErrors() {

    }
}
