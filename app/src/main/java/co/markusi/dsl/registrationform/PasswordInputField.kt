package co.markusi.dsl.registrationform

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType

@SuppressLint("ViewConstructor") // We don't want to use this View subclass in layouts
class PasswordInputField(context: Context,
                         minLength: Int,
                         maxLength: Int,
                         hint: String,
                         errorMessage: String,
                         val passwordType: PasswordType,
                         val isPasswordToggleEnabled: Boolean
) : InputField(context, minLength, maxLength, hint, errorMessage) {

    override fun isValid(): Boolean = super.isValid() && passwordType.regex.matches(editText!!.text)

    private constructor(builder: Builder)
            : this(builder.context,
            builder.minLength,
            builder.maxLength,
            builder.hint,
            builder.errorMessage,
            builder.passwordType,
            builder.isPasswordToggleEnabled) {
        editText!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        isPasswordVisibilityToggleEnabled = isPasswordToggleEnabled
    }

    class Builder private constructor(context: Context) : InputField.Builder(context) {

        var passwordType: PasswordType = PasswordType.ALPHANUMERIC
        var isPasswordToggleEnabled: Boolean = false

        override fun build() = PasswordInputField(this)

        constructor(context: Context, init: Builder.() -> Unit) : this(context) {
            init()
        }
    }

    enum class PasswordType(val regex: Regex) {
        NUMBERS_ONLY(Regex("\\d+")),
        LETTERS_ONLY(Regex("[a-zA-Z]+")),
        ALPHANUMERIC(Regex("[a-zA-Z0-9]+"))
    }
}