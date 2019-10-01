package gustavo.com.van.firebase.database.service

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import gustavo.com.van.model.User
import gustavo.com.van.storage.UserStorage

class FirebaseService {

    fun setFirstnameAndLastNameAndRoleAndEmailUserDB(user: User, currentUserDb : DatabaseReference){
        currentUserDb.child("firstName").setValue(user.firstName)
        currentUserDb.child("lastName").setValue(user.lastName)
        currentUserDb.child("role").setValue(user.role)
        currentUserDb.child("email").setValue(user.email)
    }

    fun setVanUserID(userId: String, vanReference : DatabaseReference) {
        vanReference.child("userId").setValue(userId)
    }

    fun getUser(user: User, currentUserDb : DatabaseReference, method: (user: User) -> Unit){
        currentUserDb.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val children = data!!.children
                children.forEach{
                    val userDB = (it.getValue(User::class.java) as User )
                    if(userDB.email.toString().equals(user.email.toString())){
                        UserStorage.userStorage = userDB.copy()
                        method(userDB)
                    }
                }
            }

            override fun onCancelled(data: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}