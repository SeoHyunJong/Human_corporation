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

    fun AddtoDB(d: Long, s: Int, e: Int, contents: String, case: Int): Boolean {
        var result = false
        val r = Runnable {
            val i1 = appDB?.scheduleDao()?.inspectionA(d, s, e)
            val i2 = appDB?.scheduleDao()?.inspectionB(d, s, e)
            val i3 = appDB?.scheduleDao()?.inspectionC(d, s, e)
            if(i1?.isEmpty() == true && i2?.isEmpty() == true && i3?.isEmpty() == true) {
                val price = openPrice(d)
                var currentPrice = closedPrice(d)
                if(currentPrice == 0.0){
                    currentPrice = price
                }
                appDB?.scheduleDao()?.insert(Schedule(0, d, s, e, contents, case, currentPrice + calcPrice(price, s, e, case)))
                result = true
                runOnUiThread {
                    when (case) {
                        1 -> {
                            Toast.makeText(this@MainActivity, "생산적인 일 칭찬합니다!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        2 -> {
                            Toast.makeText(this@MainActivity, "때로는 휴식이 필요한 법이죠", Toast.LENGTH_SHORT)
                                .show()
                        }
                        3 -> {
                            Toast.makeText(this@MainActivity, "알쏭달쏭한 일이었네요.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            } else{
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "다른 시간의 일과 겹칩니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val thread = Thread(r)
        thread.start()
        thread.join()
        return result
    }

    fun DeleteDB(d: Long){
        val r = Runnable{
            appDB?.scheduleDao()?.deleteAll(d)
        }
        val thread = Thread(r)
        thread.start()
    }

    fun getDB(d: Long): List<Schedule>?{
        var result: List<Schedule>? = null
        val r = Runnable{
            result = appDB?.scheduleDao()?.getAll(d)
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun openPrice(d: Long): Double{
        var result: Double = 3000.0
        val r = Runnable{
            val temp = appDB?.scheduleDao()?.openPrice(d)
            if(temp != null){
                if(temp != 0.0){
                    result = temp
                }
            }
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }
    fun calcPrice(price: Double, s: Int, e: Int, case: Int): Double{
        return when (case) {
            1 -> {
                price.times((e - s) * 0.0003)
            }
            2 -> {
                -price.times((e - s) * 0.0003)
            }
            else -> {
                0.0
            }
        }
    }
    fun maxPrice(d: Long): Double{
        var result: Double = 0.0
        val r = Runnable{
            result = appDB!!.scheduleDao().maxPrice(d)
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun minPrice(d: Long): Double{
        var result: Double = 0.0
        val r = Runnable{
            result = appDB!!.scheduleDao().minPrice(d)
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun closedPrice(d: Long): Double{
        var result: Double = 0.0
        val r = Runnable{
            result = appDB!!.scheduleDao().closedPrice(d)
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun getDate(): List<Long>?{
        var result: List<Long>? = null
        val r = Runnable{
            result = appDB?.scheduleDao()?.getDate()
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }
}