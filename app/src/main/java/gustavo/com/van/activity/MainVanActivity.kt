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
    var vanListAdapter: VanListAdapter? = null
    lateinit var recyclerView: RecyclerView
    var dayCheck = Calendar().getCalendarFormated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_van)
        recyclerView = van_list_recyclerview
        setRecyclerViewItemTouchListener()
        FirebaseService().getDayStudentsVans(Calendar().getCalendarFormated(), {
            setMultableStudentResponses(it)
        }, {
            if(dayCheck.equals(Calendar().getCalendarFormated()).not()){
                dayCheck = Calendar().getCalendarFormated()
                mapStudentResponse = mutableMapOf<String, StudentResponse>()
                list = mutableListOf<StudentResponse>()
            }
        })
    }

    fun setMultableStudentResponses(modelStudentResponseList:ModelStudentResponseList){//key: String, value: StudentResponse
        var exist = mapStudentResponse.containsKey(modelStudentResponseList.key!!)
        mapStudentResponse.put(modelStudentResponseList.key!!, modelStudentResponseList.value!!)
        if(exist.not()){
            var value = modelStudentResponseList.value!!
            list.add(value)
        }else{
            list.forEach{
                if (it.key.equals(modelStudentResponseList.key!!)) {
                    it.vou = modelStudentResponseList.value!!.vou
                    it.volto = modelStudentResponseList.value!!.volto
                }
            }
        }
        atualizarUI(list)
    }

    fun atualizarUI(list: List<StudentResponse>){
        vanListAdapter = VanListAdapter(list,this)
        recyclerView.adapter = vanListAdapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    private fun setRecyclerViewItemTouchListener() {

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or  ItemTouchHelper.DOWN, 0) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                val sourcePosition = viewHolder.adapterPosition
                val targetPosition = viewHolder1.adapterPosition
                Collections.swap(list, sourcePosition, targetPosition)
                vanListAdapter?.notifyItemMoved(sourcePosition,targetPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
//                list.removeAt(position)
//                recyclerView.adapter!!.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
