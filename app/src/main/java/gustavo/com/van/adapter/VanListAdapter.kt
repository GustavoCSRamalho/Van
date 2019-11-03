package gustavo.com.van.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import gustavo.com.van.R
import gustavo.com.van.firebase.database.service.FirebaseService
import gustavo.com.van.model.StudentResponse
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.van_item.view.*


class VanListAdapter(private val studentResponseList: List<StudentResponse>,
                     private val context: Context): Adapter<VanListAdapter.ViewHolder>() {

    var itemViewList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.van_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentResponseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val response = studentResponseList[position]
        holder?.let {
            it.bindView(response)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(studentResponse: StudentResponse){

            val vou = itemView.van_item_vou
            val volto = itemView.van_item_volto
//            val foto = itemView.van_item_title_foto
            val nome = itemView.van_item_title_nome

            vou.text = studentResponse.vou.toString()
            volto.text = studentResponse.volto.toString()
            if(studentResponse.vou.toString().equals("nao")){
                itemView.van_checkbox_vou.isEnabled = false
            }else{
                itemView.van_checkbox_vou.isEnabled = true
            }
//            foto.text = studentResponse.foto.toString()
            if(studentResponse.volto.toString().equals("nao")){
                itemView.van_checkbox_volto.isEnabled = false
            }else{
                itemView.van_checkbox_volto.isEnabled = true
            }
        }
    }
}