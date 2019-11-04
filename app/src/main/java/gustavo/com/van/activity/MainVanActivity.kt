package gustavo.com.van.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import gustavo.com.van.R
import gustavo.com.van.adapter.VanListAdapter
import gustavo.com.van.firebase.database.references.FirebaseReference
import gustavo.com.van.firebase.database.service.FirebaseService
import gustavo.com.van.model.ModelStudentResponseList
import gustavo.com.van.model.StudentResponse
import gustavo.com.van.storage.UserStorage
import gustavo.com.van.utils.Calendar
import kotlinx.android.synthetic.main.activity_main_van.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainVanActivity : AppCompatActivity() {

    var mapStudentResponse = mutableMapOf<String, StudentResponse>()
    lateinit var recyclerView: RecyclerView

    fun setMapStudentResponse(){
        mapStudentResponse = mutableMapOf<String, StudentResponse>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_van)
        recyclerView = van_list_recyclerview
        FirebaseService().getDayStudentsVans(Calendar().getCalendarFormated(), {
            setMultableStudentResponses(it)
        },{setMapStudentResponse()})
    }

    fun setMultableStudentResponses(modelStudentResponseList:ModelStudentResponseList){//key: String, value: StudentResponse
        mapStudentResponse.put(modelStudentResponseList.key!!, modelStudentResponseList.value!!)
        var list = mutableListOf<StudentResponse>()
        mapStudentResponse.forEach{
            list.add(it.value)
        }
        atualizarUI(list)
    }

    fun atualizarUI(list: List<StudentResponse>){
        recyclerView.adapter = VanListAdapter(list,this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
}
