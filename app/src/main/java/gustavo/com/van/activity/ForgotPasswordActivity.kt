package gustavo.com.van.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gustavo.com.van.R
import gustavo.com.van.firebase.auth.service.FirebaseAuthService
import gustavo.com.van.model.User
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btn_submit!!.setOnClickListener{
            FirebaseAuthService().sendPasswordResetEmail(getUser(),{updateUI()}, this@ForgotPasswordActivity)
        }
    }

    fun getUser(): User{
        val email = et_email?.text.toString()
        return User(email = email)
    }

    fun updateUI() {
        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
