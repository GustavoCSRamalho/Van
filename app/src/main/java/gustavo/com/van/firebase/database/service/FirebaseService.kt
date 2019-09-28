package gustavo.com.van.firebase.database.service

import com.google.firebase.database.DatabaseReference
import gustavo.com.van.model.User

class FirebaseService {

    fun setFirstnameAndLastNameUserDB(user: User, currentUserDb : DatabaseReference){
        currentUserDb.child("firstName").setValue(user.firstName)
        currentUserDb.child("lastName").setValue(user.lastName)
    }
}