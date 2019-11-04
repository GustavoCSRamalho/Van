package gustavo.com.van.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gustavo.com.van.R
import gustavo.com.van.firebase.auth.FirebaseInitializer
import gustavo.com.van.firebase.auth.service.FirebaseAuthService
import gustavo.com.van.firebase.database.service.FirebaseService
import gustavo.com.van.model.StudentResponse
import gustavo.com.van.model.User
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    private var mProgressBar: ProgressDialog? = null
    var listOfVans = mutableListOf<String>()

    fun setListOfVans(){
        listOfVans = mutableListOf<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mProgressBar = ProgressDialog(this)
        btn_register!!.setOnClickListener { createNewAccount() }

        FirebaseService().getVansNames({
            listOfVans.add(it)
        },{
            setListOfVans()
        })
    }

    fun createNewAccount(){
        val user = getUser()
        if ((TextUtils.isEmpty(user.firstName).not()).and((TextUtils.isEmpty(user.lastName).not())).and((
                    TextUtils.isEmpty(user.email).not())).and((TextUtils.isEmpty(user.password).not())).and((user.role.equals("Selecione um papel").not()))) {
            if(user.role.equals("Van")){
                var foundVan = user.van in listOfVans
                if(foundVan){
                    Toast.makeText(this, "Nome ja cadastrado!", Toast.LENGTH_SHORT).show()
                }else {
                    createUser(user)
                }
            }else if(user.role.equals("Estudante")){
                var foundVanStudent = user.van in listOfVans
                if(foundVanStudent.not()){
                    Toast.makeText(this, "Van não cadastrada!", Toast.LENGTH_SHORT).show()
                }else{
                    createUser(user)
                }
            }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    fun createUser(user: User){
        progressBar()
        FirebaseAuthService().createUserWithEmailAndPassword(user,{
            mProgressBar!!.hide()
        }, {
            updateUserInfoAndUI()
        }, this@CreateAccountActivity)
    }

    fun getUser(): User{
        val firstName = et_first_name?.text.toString()
        val lastName = et_last_name?.text.toString()
        val email = et_email?.text.toString()
        val password = et_password?.text.toString()
        val role = roleSelect.selectedItem.toString()
        val van = et_van?.text.toString()
        //TODO: Deixar em um input porteriomente

        return User(firstName, lastName,password,email,role, van)
    }

    fun updateUserInfoAndUI() {
////        //start next activity
////        val intent = Intent(this@CreateAccountActivity, CreateAccountActivity::class.java)
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////        startActivity(intent)
    }

    fun progressBar(){
        mProgressBar!!.setMessage("Registrando Usuario...")
        mProgressBar!!.show()
    }
}
