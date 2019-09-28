package gustavo.com.van.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gustavo.com.van.R
import gustavo.com.van.firebase.auth.FirebaseInitializer
import gustavo.com.van.firebase.auth.service.FirebaseAuthService
import gustavo.com.van.model.User
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    private var mProgressBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mProgressBar = ProgressDialog(this)
        btn_register!!.setOnClickListener { createNewAccount() }
    }

    fun createNewAccount(){
        val user = getUser()
        if ((TextUtils.isEmpty(user.firstName).not()).and((TextUtils.isEmpty(user.lastName).not())).and((
                    TextUtils.isEmpty(user.email).not())).and((TextUtils.isEmpty(user.password).not()))) {
            progressBar()
            FirebaseAuthService().createUserWithEmailAndPassword(user,{
                mProgressBar!!.hide()
            }, {
                updateUserInfoAndUI()
            }, this@CreateAccountActivity)
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUser(): User{
        val firstName = et_first_name?.text.toString()
        val lastName = et_last_name?.text.toString()
        val email = et_email?.text.toString()
        val password = et_password?.text.toString()

        return User(firstName, lastName,password,email)
    }

    fun updateUserInfoAndUI() {
//        //start next activity
//        val intent = Intent(this@CreateAccountActivity, CreateAccountActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        startActivity(intent)
    }

    fun progressBar(){
        mProgressBar!!.setMessage("Registering User...")
        mProgressBar!!.show()
    }
}
