package gustavo.com.van.activity

import android.annotation.TargetApi
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_student_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DatabaseReference
import gustavo.com.van.R
import gustavo.com.van.firebase.auth.service.FirebaseAuthService
import gustavo.com.van.firebase.database.references.FirebaseReference
import gustavo.com.van.firebase.database.service.FirebaseService
import gustavo.com.van.storage.UserStorage
import java.text.DateFormat

import java.text.SimpleDateFormat


class MainStudentActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val C1 = Color.YELLOW
    private val C2 = Color.GREEN

    var vanReference:DatabaseReference? = null
    var uid: String? = null

    var buttonVouColor: Int? = Color.LTGRAY
    var buttonVoltoColor: Int? = Color.LTGRAY

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(gustavo.com.van.R.layout.activity_student_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        navView!!.setNavigationItemSelectedListener(this)

        calendarData.text = getCalendarFormated()

        println("User role : ")
        println(UserStorage.userStorage?.van.toString())
        vanReference = FirebaseReference().getVanReferenceVans(UserStorage.userStorage?.van.toString())

        FirebaseService().getStudentVan({
            setVou()
        },{
            setVolto()
        },{
            setStudentResponse()
        })

        uid = FirebaseAuthService().getUserID()
//        FirebaseService().setUserIDVans(uid,vanReference)

//        buttonVou.setBackgroundColor(Color.GRAY)
//        buttonVou.background.colorFilter = PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
//        buttonVouColor = Color.LTGRAY
        buttonVou.setOnClickListener{
            if(buttonVouColor == Color.LTGRAY){
                buttonVou.background.colorFilter = PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN)
                buttonVouColor = Color.GREEN
                FirebaseService().setVouVans(uid!!,vanReference!!,"sim",getCalendarFormated())
//                println("Data teste : ")
//                println(getCalendarFormated())
            }else{
                buttonVou.background.colorFilter = PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
                buttonVouColor = Color.LTGRAY
                FirebaseService().setVouVans(uid!!,vanReference!!,"nao",getCalendarFormated())
//            }
        }

////            val color = buttonVou.background as ColorDrawable
////            if(color.color == Color.GRAY){
////                buttonVou.setBackgroundColor(Color.GREEN)
////                FirebaseService().setVouVans(uid,vanReference,"sim",getCalendarFormated())
//////                println("Data teste : ")
//////                println(getCalendarFormated())
////            }else{
////                buttonVou.setBackgroundColor(Color.GRAY)
////                FirebaseService().setVouVans(uid,vanReference,"nao",getCalendarFormated())
////            }
        }



//        buttonVolto.background.colorFilter = PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
//        buttonVoltoColor = Color.LTGRAY

        buttonVolto.setOnClickListener{
            if(buttonVoltoColor == Color.LTGRAY){
                buttonVolto.background.colorFilter = PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN)
                buttonVoltoColor = Color.GREEN
                FirebaseService().setVoltoVans(uid!!,vanReference!!,"sim",getCalendarFormated())
            }else{
                buttonVolto.background.colorFilter = PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
                buttonVoltoColor = Color.LTGRAY
                FirebaseService().setVoltoVans(uid!!,vanReference!!,"nao",getCalendarFormated())
            }
        }
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

    fun setVou(){
        buttonVou.background.colorFilter = PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN)
        buttonVouColor = Color.GREEN
    }

    fun setVolto(){
        buttonVolto.background.colorFilter = PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN)
        buttonVoltoColor = Color.GREEN
    }

    fun setStudentResponse(){
        FirebaseService().setVouVans(uid!!,vanReference!!,"nao",getCalendarFormated())
        FirebaseService().setVoltoVans(uid!!,vanReference!!,"nao",getCalendarFormated())
    }

    fun drawerInitializer(): ActionBarDrawerToggle{
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        return toggle
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START)!!) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> {
                Toast.makeText(this, "Em construção!", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_item_two -> {
                Toast.makeText(this, "Em construção!", Toast.LENGTH_SHORT).show()
            }
//            R.id.nav_item_three -> {
//                Toast.makeText(this, "Menu 3", Toast.LENGTH_SHORT).show()
//            }
//            R.id.nav_item_four -> {
//                Toast.makeText(this, "Menu 4", Toast.LENGTH_SHORT).show()
//            }
            else -> {
                Toast.makeText(this, "Menu Default", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true

    }
}
