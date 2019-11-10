package gustavo.com.van.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import gustavo.com.van.R
import gustavo.com.van.adapter.VanListAdapter
import gustavo.com.van.firebase.database.service.FirebaseService
import gustavo.com.van.model.ModelStudentResponseList
import gustavo.com.van.utils.Calendar
import kotlinx.android.synthetic.main.activity_main_van.*
import java.util.*

import gustavo.com.van.model.StudentResponse

class MainVanActivity : AppCompatActivity() {

    var mapStudentResponse = mutableMapOf<String, StudentResponse>()
    var list = mutableListOf<StudentResponse>()
    var listOrdered = mutableMapOf<String,StudentResponse>()
    var vanListAdapter: VanListAdapter? = null
    lateinit var recyclerView: RecyclerView
    var dayCheck = Calendar().getCalendarFormated()

    fun setMapStudentResponse(){
        mapStudentResponse = mutableMapOf<String, StudentResponse>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_van)
        recyclerView = van_list_recyclerview
        setRecyclerViewItemTouchListener()
        FirebaseService().getDayStudentsVans(Calendar().getCalendarFormated(), {
            setMultableStudentResponses(it)
        },{setMapStudentResponse()}, {
            if(dayCheck.equals(Calendar().getCalendarFormated()).not()){
//                listOrdered = mutableMapOf<String,StudentResponse>()
                dayCheck = Calendar().getCalendarFormated()
                setMapStudentResponse()
                list = mutableListOf<StudentResponse>()
            }
        })
    }

    fun setMultableStudentResponses(modelStudentResponseList:ModelStudentResponseList){//key: String, value: StudentResponse
        var exist = mapStudentResponse.containsKey(modelStudentResponseList.key!!)
        println("Exist : ")
        println(exist)
        mapStudentResponse.put(modelStudentResponseList.key!!, modelStudentResponseList.value!!)
//        var novaLista = list
        if(exist.not()){
            var value = modelStudentResponseList.value!!
            println("Value ")
            println(value)
            list.add(value)
        }else{
            list.forEach{
                if (it.key.equals(modelStudentResponseList.key!!)) {
                    it.vou = modelStudentResponseList.value!!.vou
                    it.volto = modelStudentResponseList.value!!.volto
                }
            }
        }

        println("KEY : ")
        println(modelStudentResponseList.value!!.key)
        println("Itens : ")
        list.forEach { println(it) }
//        var listNew = list.map{
//            s
//            if(it.key.equals(modelStudentResponseList.key!!)){
//                 it.vou = modelStudentResponseList.value!!.vou.toString()
//                 it.volto = modelStudentResponseList.value!!.volto.toString()
//                return it
//            }else{
//                return it
//            }
//        }.toList()
//        mapStudentResponse

//        list = mutableListOf<StudentResponse>()

//        mapStudentResponse.forEach{
////            if(listOrdered.contains(it.key).not()){
////                listOrdered.put(it.key,listOrdered.size + 1)
////            }
//            list.add(it.value)
//        }
//        val mapOrdered = listOrdered.toList().sortedBy { (key, value) -> value }.toMap()
//        mapOrdered.forEach{
//            val student = mapStudentResponse.get(it.key)
//            list.add(student!!)
//        }

        atualizarUI(list)
    }

    fun atualizarUI(list: List<StudentResponse>){
        vanListAdapter = VanListAdapter(list,this)
        recyclerView.adapter = vanListAdapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

//        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        recyclerView.addItemDecoration(dividerItemDecoration)
//        setRecyclerViewItemTouchListener()
    }

    private fun setRecyclerViewItemTouchListener() {

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or  ItemTouchHelper.DOWN, 0) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                val sourcePosition = viewHolder.adapterPosition
                val targetPosition = viewHolder1.adapterPosition
                Collections.swap(list, sourcePosition, targetPosition)
                println("SourcePosition : ")
                println(sourcePosition)
                println("targetPosition : ")
                println(targetPosition)

                vanListAdapter?.notifyItemMoved(sourcePosition,targetPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val position = viewHolder.adapterPosition
//                list.removeAt(position)
//                recyclerView.adapter!!.notifyItemRemoved(position)
            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
