package com.example.humancorporation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.humancorporation.navigation.AddFragment
import com.example.humancorporation.navigation.GraphFragment
import com.example.humancorporation.navigation.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_graph.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(){
    private var appDB: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appDB = AppDatabase.getInstance(this)

        navigation.run{
            setOnItemSelectedListener { item-> //navigation 메뉴 클릭 이벤트
                when(item.itemId){
                    R.id.action_home -> {
                        val homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, homeFragment).commit()
                    }
                    R.id.action_graph -> {
                        val graphFragment = GraphFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, graphFragment).commit()

                    }
                    R.id.action_add -> {
                        val addFragment = AddFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, addFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.action_home //처음에 제공되는 화면
        }

    }

    fun toAddFragment() {
        navigation.selectedItemId = R.id.action_add //add 메뉴가 탭된것 처럼
        //val addFragment = AddFragment()
        //supportFragmentManager.beginTransaction().replace(R.id.main_content, addFragment).commit()
    }

    fun productiveAdd(d: String, s: Int, e: Int, contents: String) {
        var lst: List<Schedule>? = null

        val r = Runnable {
            appDB?.scheduleDao()?.insert(Schedule(0, d, s, e, contents))
        }
        val thread = Thread(r)
        thread.start()

    }
}