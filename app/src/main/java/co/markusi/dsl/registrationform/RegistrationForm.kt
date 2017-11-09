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
                                           val usernameField: InputField,
                                           val passwordField: PasswordInputField,
                                           val dropdownMenu: DropdownMenu,
                                           val checkBoxes: List<FormCheckBox>,
                                           val registerButton: RegisterButton)
    : LinearLayout(context) {

    private constructor(builder: Builder) : this(
            builder.context,
            builder.usernameField,
            builder.passwordField,
            builder.dropdownMenu,
            builder.checkBoxes,
            builder.registerButton) {
        orientation = VERTICAL
        val formPadding = context.resources.getDimensionPixelOffset(R.dimen.spacing_2x)
        setPadding(formPadding, formPadding, formPadding, formPadding)
        addView(usernameField)
        addView(passwordField)
        addView(dropdownMenu)
        builder.checkBoxes.forEach {
            addView(it)
        }
        addView(registerButton)
    }

    companion object {
        fun create(context: Context, init: Builder.() -> Unit) = Builder(context, init).build()
    }

    fun isValid(): Boolean {
        return usernameField.isValid() && passwordField.isValid() && allMandatoryCheckBoxesAreChecked()
    }

    private fun allMandatoryCheckBoxesAreChecked(): Boolean = checkBoxes.all { it.isValid() }


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

        fun usernameField(init: InputField.Builder.() -> Unit) {
            usernameField = InputField.Builder(context, init).build()
            applyDefaultFieldAttributes(usernameField)
        }

        fun passwordField(init: PasswordInputField.Builder.() -> Unit) {
            passwordField = PasswordInputField.Builder(context, init).build()
            applyDefaultFieldAttributes(passwordField)
        }

        fun dropdownMenu(init: DropdownMenu.Builder.() -> Unit) {
            dropdownMenu = DropdownMenu.Builder(context, init).build()
            applyDefaultFieldAttributes(dropdownMenu)
        }

        fun formCheckBox(init: FormCheckBox.Builder.() -> Unit) : FormCheckBox {
            val formCheckBox = FormCheckBox.Builder(context, init).build()
            applyDefaultFieldAttributes(formCheckBox)
            checkBoxes.add(formCheckBox)
            return formCheckBox
        }

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
        usernameField.hideError()
        passwordField.hideError()
        checkBoxes.forEach {
            it.hideError()
        }
    }

    fun showErrors() {
        usernameField.showErrorIfInvalid()
        passwordField.showErrorIfInvalid()
        checkBoxes.forEach {
            it.showErrorIfInvalid()
        }
    }
}
