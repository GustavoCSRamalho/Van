package gustavo.com.van.firebase.database

import com.google.firebase.database.FirebaseDatabase

class FirebaseInitializer {

    private var mDatabase: FirebaseDatabase? = null

    fun getInstance(): FirebaseDatabase?{
        if(mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance()
        }
        return mDatabase
    }

}