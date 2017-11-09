package co.markusi.dsl

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
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