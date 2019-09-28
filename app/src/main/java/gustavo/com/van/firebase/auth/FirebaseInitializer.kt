package gustavo.com.van.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseInitializer {

    private var mDatabase: FirebaseAuth? = null

    fun getInstance(): FirebaseAuth?{
        if(mDatabase == null){
            mDatabase = FirebaseAuth.getInstance()
        }
        return mDatabase
    }

}