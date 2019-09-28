package gustavo.com.van.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import gustavo.com.van.R
import gustavo.com.van.firebase.auth.service.FirebaseAuthService
import gustavo.com.van.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    private var mProgressBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mProgressBar = ProgressDialog(this)

        tv_forgot_password!!
            .setOnClickListener { startActivity(
                Intent(this@LoginActivity,
                ForgotPasswordActivity::class.java)
            ) }
        btn_register_account!!.setOnClickListener { startActivity(
            Intent(this@LoginActivity,
                CreateAccountActivity::class.java)
        ) }

        btn_login!!.setOnClickListener{loginUser()}
    }

    fun getUser(): User{
        val email = et_email?.text.toString()
        val password = et_password?.text.toString()
        return User(email = email, password = password,firstName = "", lastName = "")
    }

    fun loginUser() {
        val user = getUser()
        if ((TextUtils.isEmpty(user.email).not()).and((TextUtils.isEmpty(user.password).not()))) {
            progressBar()
            Log.d(TAG, "Logging in user.")
            FirebaseAuthService().signInWithEmailAndPassword(user,{
                mProgressBar!!.hide()
            }, TAG,this@LoginActivity, {updateUI()})
        }else{
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateUI() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun progressBar(){
        mProgressBar!!.setMessage("Registering User...")
        mProgressBar!!.show()
    }
}
