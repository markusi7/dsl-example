package co.markusi.dsl.registrationform

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import co.markusi.dsl.R

@SuppressLint("ViewConstructor") // We don't want to use this View subclass in layouts
@RegistrationFormMarker
class RegisterButton(context: Context) : Button(context) {

    private constructor(builder: RegisterButton.Builder)
            : this(builder.context) {
        text = builder.text
        setOnClickListener {
            builder.onClickListener.invoke((parent as RegistrationForm).isValid())
        }
    }

    class Builder private constructor(val context: Context) {

        var text: String = context.getString(R.string.register).toUpperCase()
        lateinit var onClickListener: (Boolean) -> Unit

        constructor(context: Context, init: Builder.() -> Unit) : this(context) {
            init()
        }

        fun build() = RegisterButton(this)
    }
}