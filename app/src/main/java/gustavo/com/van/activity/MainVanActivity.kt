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
import kotlinx.android.synthetic.main.activity_main_van.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainVanActivity : AppCompatActivity() {

    var mapStudentResponse = mutableMapOf<String, StudentResponse>()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_van)
        recyclerView = van_list_recyclerview
        FirebaseService().getDayStudentsVans(getCalendarFormated(), {
            setMultableStudentResponses(it)
        })



//        recyclerView.adapter = VanListAdapter(notes(),this)
//        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
//        recyclerView.layoutManager = layoutManager
    }

    fun setMultableStudentResponses(modelStudentResponseList:ModelStudentResponseList){//key: String, value: StudentResponse
        mapStudentResponse.put(modelStudentResponseList.key!!, modelStudentResponseList.value!!)
        var list = mutableListOf<StudentResponse>()
        mapStudentResponse.forEach{
            println("Information studentResponse")
            println(it.value)
            list.add(it.value)
        }
        atualizarUI(list)
        println("Teste surpresa !!")
        println(modelStudentResponseList.toString())
    }

    fun atualizarUI(list: List<StudentResponse>){
        recyclerView.adapter = VanListAdapter(list,this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }


    private fun notes(): List<StudentResponse>{
        return listOf(
            StudentResponse("sim","nao"),
            StudentResponse("sim","sim"),
            StudentResponse("nao","nao")
        )
    }


    fun getCalendarFormated():String {
        val date = Date()
        val sdf: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        val dateParsed = sdf.parse(date.toString())


//        val novaData=  sdf.parse(sdf.format(date)) as Date


//        val calendar: Calendar = Calendar.getInstance()
//        calendar.time = novaData
//        val newDate: Date = calendar.time
//        println("Calendar : ")
//        println(calendar.time.toString())
//        println("Testando date")
//        println(newDate.toString())
//        return novaData.toString()

        return sdf.format(date)
    }
}
