package gustavo.com.van.activity

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
import android.graphics.drawable.ColorDrawable
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
        val vanReference = FirebaseReference().getVanReferenceVans(UserStorage.userStorage?.van.toString())

        val uid = FirebaseAuthService().getUserID()
//        FirebaseService().setUserIDVans(uid,vanReference)

        buttonVou.setBackgroundColor(Color.GRAY)
        buttonVou.setOnClickListener{
            val color = buttonVou.background as ColorDrawable
            if(color.color == Color.GRAY){
                buttonVou.setBackgroundColor(Color.GREEN)
                FirebaseService().setVouVans(uid,vanReference,"sim",getCalendarFormated())
//                println("Data teste : ")
//                println(getCalendarFormated())
            }else{
                buttonVou.setBackgroundColor(Color.GRAY)
                FirebaseService().setVouVans(uid,vanReference,"nao",getCalendarFormated())
            }
        }



        buttonVolto.setBackgroundColor(Color.GRAY)
        buttonVolto.setOnClickListener{
            val color = buttonVolto.background as ColorDrawable
            if(color.color == Color.GRAY){
                buttonVolto.setBackgroundColor(Color.GREEN)
                FirebaseService().setVoltoVans(uid,vanReference,"sim",getCalendarFormated())
            }else{
                buttonVolto.setBackgroundColor(Color.GRAY)
                FirebaseService().setVoltoVans(uid,vanReference,"nao",getCalendarFormated())
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
                Toast.makeText(this, "Menu 1", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_item_two -> {
                Toast.makeText(this, "Menu 2", Toast.LENGTH_SHORT).show()
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
