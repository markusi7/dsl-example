package co.markusi.dsl

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import co.markusi.dsl.registrationform.PasswordInputField
import co.markusi.dsl.registrationform.RegistrationForm

class MainActivity : AppCompatActivity() {

    private lateinit var registrationForm: RegistrationForm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Toolbar>(R.id.toolbar).title = getString(R.string.registration_form)

        addRegistrationForm()
    }

    private fun addRegistrationForm() {
        registrationForm = RegistrationForm.create(this) {

            usernameField {
                maxLength = 32
                hint = getString(R.string.username).capitalize()
                errorMessage = getString(R.string.username_error_message).format(minLength, maxLength)
            }

            passwordField {
                passwordType = PasswordInputField.PasswordType.NUMBERS_ONLY
                minLength = 4
                maxLength = 16
                hint = getString(R.string.password_hint)
                isPasswordToggleEnabled = true
                errorMessage = getString(R.string.only_numbers_are_allowed)
            }

            dropdownMenu {
                text = getString(R.string.writing_dsls_in_kotlin_is)
                items = resources.getStringArray(R.array.dropdown_items).map { it }
                selectedItem = getString(R.string.preselected_dropdown_item)
            }

            formCheckBox {
                text = getString(R.string.i_am_not_mandatory)
            }

            formCheckBox {
                text = getString(R.string.i_will_consider_using_dsls)
                isMandatory = true
            }

            registerButton {
                onClickListener = {
                    registrationForm.hideErrors()
                    if (it) {
                        showSuccessDialog()
                    } else {
                        registrationForm.showErrors()
                    }
                }
            }
        }
        findViewById<ViewGroup>(R.id.root).addView(registrationForm)
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.registration_successful)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .show()
    }
}