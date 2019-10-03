package gustavo.com.van.firebase.database.references

import com.google.firebase.database.DatabaseReference
import gustavo.com.van.firebase.database.FirebaseInitializer

class FirebaseReference {

    fun getChildReferenceUserDB(): DatabaseReference{
        return FirebaseInitializer().getInstance()!!.reference!!.child("Users")
    }

    fun getChildReferenceIdUserDB(userId: String): DatabaseReference{
        return getChildReferenceUserDB()!!.child(userId)
    }

    fun getVanReferenceVans(): DatabaseReference {
        return FirebaseInitializer().getInstance()!!.reference!!.child("Vans")
    }

    fun getVanReferenceVans(van: String): DatabaseReference{
        return getVanReferenceVans()!!.child(van)
    }

}