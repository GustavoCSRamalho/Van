package gustavo.com.van.firebase.auth.service

import android.app.ProgressDialog
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.google.firebase.auth.FirebaseAuth
import gustavo.com.van.firebase.auth.FirebaseInitializer
import gustavo.com.van.firebase.database.references.FirebaseReference
import gustavo.com.van.firebase.database.service.FirebaseService
import gustavo.com.van.model.User

class FirebaseAuthService {

    private val TAG = "CreateAccountActivity"

    var mAuth: FirebaseAuth? = null

    init{
        mAuth = FirebaseInitializer().getInstance()!!
    }

    fun createUserWithEmailAndPassword(user: User, mProgressBar: () -> Unit,
                                       updateUserInfoAndUI: () -> Unit,
                                        context: Context){
        mAuth!!.createUserWithEmailAndPassword(user.email!!,user.password!!).addOnCompleteListener {
            task ->
            mProgressBar()
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                val userId = mAuth!!.currentUser!!.uid
                verifyEmail(context)
                val currentUserDb = FirebaseReference().getChildReferenceId(userId)
                FirebaseService().setFirstnameAndLastNameUserDB(user,currentUserDb)
//                updateUserInfoAndUI()
            } else {
                Toast.makeText(context, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
            }

        }
    }

    fun verifyEmail(context: Context) {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification().addOnCompleteListener{
            task ->
            if (task.isSuccessful) {
                Toast.makeText(context,
                    "Verification email sent to " + mUser.getEmail(),
                    Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "sendEmailVerification", task.exception)
                Toast.makeText(
                    context,
                    "Failed to send verification email.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun signInWithEmailAndPassword(user: User,progressBar: () -> Unit,TAG: String,
                                   context: Context, updateUI: () -> Unit){
        mAuth!!.signInWithEmailAndPassword(user.email!!, user.password!!).addOnCompleteListener{
            task -> progressBar()
            if(task.isSuccessful){
                Log.d(TAG,"signInWithEmail:success")
                updateUI()
            }else{
                Log.e(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(context, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun sendPasswordResetEmail(user: User, updateUI: () -> Unit,context: Context){
        val email = user.email.toString()
        if((TextUtils.isEmpty(user.email).not())){
            mAuth!!.sendPasswordResetEmail(email).addOnCompleteListener{
                task ->
                if (task.isSuccessful) {
                    val message = "Email sent."
                    Log.d(TAG, message)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                    updateUI()
                } else {
                    Log.w(TAG, task.exception!!.message)
                    Toast.makeText(context, "No user found with this email.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}