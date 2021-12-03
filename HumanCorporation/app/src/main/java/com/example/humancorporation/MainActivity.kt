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
import kotlin.math.abs

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
                appDB?.scheduleDao()?.insert(Schedule(0, d, s, e, contents, case))
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
            appDB?.stockDAO()?.deleteAll(d)
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

    fun calcPrice(price: Float, s: Int, e: Int, case: Int): Float{
        val minute = ((e/100)*60+e%100) - ((s/100)*60+s%100)
        return when (case) {
            1 -> {
                price.times(minute * 0.0003f)
            }
            2 -> {
                -price.times(minute * 0.0003f)
            }
            else -> {
                0f
            }
        }
    }

    fun openPrice(d: Long): Float{
        var result: Float = 3000f
        val r = Runnable{
            val temp = appDB!!.stockDAO().getOpenPrice(d)
            if(temp != 0f){
                result = temp
            }
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun completeDay(d: Long) {
        val lst = getDB(d)
        val price = openPrice(d)
        var currentPrice = price
        val priceLst = ArrayList<Float>()
        if(!lst.isNullOrEmpty()){
            for(item in lst){
                val s = item.startTime
                val e = item.endTime
                val case = item.typeNum
                currentPrice += calcPrice(price, s, e, case)
                priceLst.add(currentPrice)
            }
            val high = priceLst.maxOrNull()
            val low = priceLst.minOrNull()
            val r = Runnable{
                appDB?.stockDAO()?.insert(CSStock(d, price, currentPrice, high!!, low!!))
            }
            val thread = Thread(r)
            thread.start()
        }
    }

    fun getAllStock(): List<CSStock>?{
        var result: List<CSStock>? = null
        val r = Runnable{
            result = appDB?.stockDAO()?.getAll()
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun todayPrice(): Float{
        var result: Float = 3000f
        val r = Runnable{
            val temp = appDB!!.stockDAO().getTodayPrice()
            if(temp != 0f){
                result = temp
            }
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun todayOpenPrice(): Float{
        var result: Float = 3000f
        val r = Runnable{
            val temp = appDB!!.stockDAO().getTodayOpenPrice()
            if(temp != 0f){
                result = temp
            }
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun maxPrice(): Float{
        var result: Float = 3000f
        val r = Runnable{
            val temp = appDB!!.stockDAO().getMaxPrice()
            if(temp != 0f){
                result = temp
            }
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }

    fun minPrice(): Float{
        var result: Float = 3000f
        val r = Runnable{
            val temp = appDB!!.stockDAO().getMinPrice()
            if(temp != 0f){
                result = temp
            }
        }
        val thread = Thread(r)
        thread.start()
        thread.join() // thread가 끝날때까지 기다린다.
        return result
    }
}