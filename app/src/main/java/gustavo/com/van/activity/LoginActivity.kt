package gustavo.com.van.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import gustavo.com.van.R
import gustavo.com.van.firebase.auth.service.FirebaseAuthService
import gustavo.com.van.model.User
import gustavo.com.van.storage.UserStorage
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password

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

        btn_login!!.setOnClickListener{
            try{
                loginUser()
            }catch (e: Exception){
                Toast.makeText(this, e.fillInStackTrace().toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getUser(): User{
        val email = et_email?.text.toString()
        val password = et_password?.text.toString()
        return User(email = email, password = password)
    }

    fun loginUser() {
        val user = getUser()
        if ((TextUtils.isEmpty(user.email).not()).and((TextUtils.isEmpty(user.password).not()))) {
            progressBar()
            Log.d(TAG, "Logging in user.")
            FirebaseAuthService().signInWithEmailAndPassword(user,{
                mProgressBar!!.hide()
            }, TAG,this@LoginActivity, {updateUI(it)})
        }else{
            Toast.makeText(this, "Preencha todos os dados!", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateUI(user: User) {
        var intent: Intent? = null
        if(user.role.equals("Estudante")){
            intent = Intent(this@LoginActivity, MainStudentActivity::class.java)
//            Toast.makeText(this@LoginActivity, "Student",Toast.LENGTH_LONG).show()
        }else{
            intent = Intent(this@LoginActivity, MainVanActivity::class.java)
//            Toast.makeText(this@LoginActivity, "Van",Toast.LENGTH_LONG).show()
        }
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun progressBar(){
        mProgressBar!!.setMessage("Logando Usuario...")
        mProgressBar!!.show()
    }
}
