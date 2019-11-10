package gustavo.com.van.firebase.database.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import gustavo.com.van.firebase.auth.FirebaseInitializer
import gustavo.com.van.firebase.auth.service.FirebaseAuthService
import gustavo.com.van.firebase.database.references.FirebaseReference
import gustavo.com.van.model.ModelStudentResponseList
import gustavo.com.van.model.StudentResponse
import gustavo.com.van.model.User
import gustavo.com.van.storage.UserStorage
import gustavo.com.van.utils.Calendar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FirebaseService {

    var mAuth: FirebaseAuth? = null

    init{
        mAuth = FirebaseInitializer().getInstance()!!
    }

    fun setFirstnameAndLastNameAndRoleAndEmailAndVanUserDB(user: User){
        val userId = mAuth!!.currentUser!!.uid
        val currentUserDb = FirebaseReference().getChildReferenceIdUserDB(userId)

        currentUserDb.child("firstName").setValue(user.firstName)
        currentUserDb.child("lastName").setValue(user.lastName)
        currentUserDb.child("role").setValue(user.role)
        currentUserDb.child("email").setValue(user.email)
        currentUserDb.child("van").setValue(user.van)
    }

    fun setVouVans(userId: String, vanReference : DatabaseReference,vou: String,date: String) {
        val userId = mAuth!!.currentUser!!.uid
        vanReference.child(date.replace("/","_")).child(userId).child("key").setValue(userId)
        vanReference.child(date.replace("/","_")).child(userId).child("vou").setValue(vou)
    }

    fun setVoltoVans(userId: String, vanReference : DatabaseReference,volto: String,date: String) {
        val userId = mAuth!!.currentUser!!.uid
        vanReference.child(date.replace("/","_")).child(userId).child("key").setValue(userId)
        vanReference.child(date.replace("/","_")).child(userId).child("volto").setValue(volto)
    }

    fun getStudentVan(setVou:() -> Unit,setVolto:() -> Unit,setStudentResponse: () -> Unit){
        val date = Calendar().getCalendarFormated()
        val vanReference = FirebaseReference().getVanReferenceVans(UserStorage.userStorage?.van.toString())
        vanReference.child(date.replace("/","_")).child(FirebaseAuthService().getUserID()).addListenerForSingleValueEvent(
            object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(data: DataSnapshot) {
                println(data.toString())
                if(data.value == null){
                    setStudentResponse()
                }else{
                    var studentResponse = data.getValue(StudentResponse::class.java) as StudentResponse
                    if(studentResponse.vou.equals("sim")){
                        setVou()
                    }
                    if(studentResponse.volto.equals("sim")){
                        setVolto()
                    }
                }
            }
        })
    }


    fun getDayStudentsVans(date: String, metodo:(modelStudentResponseList: ModelStudentResponseList) -> Unit,
                           setClearVariables: () -> Unit){
        val vanReference = FirebaseReference().getVanReferenceVans(UserStorage.userStorage?.van.toString())
        vanReference.child(date.replace("/","_")).
            addValueEventListener(object : ValueEventListener{
                override fun onDataChange(data: DataSnapshot) {
                    setClearVariables()
                    val children = data.children
                    children.forEach{
                        var studentResponse = it.getValue(StudentResponse::class.java) as StudentResponse
                        setUserInformationsIntoStudentResponse(it.key.toString(),studentResponse,{
                            metodo(it)
                        })
                    }
                }
                override fun onCancelled(data: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }

    fun getVansNames(setListVan:(valor: String) -> Unit, setListOfVans:() -> Unit){
        setListOfVans()
        val firebaseReference = FirebaseReference().getVanReferenceVans()
        firebaseReference.addListenerForSingleValueEvent(object:  ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(data: DataSnapshot) {
                val children = data.children
                children.forEach{
                    val key = it.key.toString()
                    setListVan(key)
                }
            }
        })
    }

    fun setUserInformationsIntoStudentResponse(userId: String, studentResponse: StudentResponse?,
                                               metodo: (modelStudentResponseList: ModelStudentResponseList) -> Unit){
        val firebaseReference = FirebaseReference().getChildReferenceIdUserDB(userId)
        firebaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(data: DataSnapshot) {
                var user = data.getValue(User::class.java) as User

                studentResponse!!.nomeESobrenome = user.firstName + " " + user.lastName
                studentResponse!!.foto = "Sua foto aqui"
                metodo(ModelStudentResponseList(key = data.key.toString(),value = studentResponse.copy()))
            }
        })
    }

    fun getUser(user: User, method: (user: User) -> Unit){
        val currentUserDb  = FirebaseReference().getChildReferenceUserDB()
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