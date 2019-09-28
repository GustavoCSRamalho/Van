package gustavo.com.van.firebase.database.references

import com.google.firebase.database.DatabaseReference
import gustavo.com.van.firebase.database.FirebaseInitializer

class FirebaseReference {

    fun getChildReference(): DatabaseReference{
        return FirebaseInitializer().getInstance()!!.reference!!.child("Users")
    }

    fun getChildReferenceId(userId: String): DatabaseReference{
        return getChildReference()!!.child(userId)
    }
}