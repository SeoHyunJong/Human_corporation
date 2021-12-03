package com.example.humancorporation.navigation

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.humancorporation.MainActivity
import com.example.humancorporation.R
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import kotlin.math.round

class HomeFragment : Fragment(){
    var dateLong: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
        //inflate란? xml 파일을 메모리에 객체화 시키는 것 -> 즉, xml에 정의된 object들을 kt 파일에서 다룰 수 있게
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_addday.setOnClickListener {
            (activity as MainActivity).toAddFragment()
        }
        val date = LocalDate.now() //현재의 날짜 정보를 받아온다
        calcDateLong(date.year, date.monthValue, date.dayOfMonth)
        val currentPrice = (activity as MainActivity).closedPrice(dateLong)
        val openPrice = (activity as MainActivity).openPrice(dateLong)

        life_price.text = "${round(currentPrice).toInt()} 원"
        calcPer(currentPrice, openPrice)

        //childFragmentManager.beginTransaction().replace(R.id.graph_container, GraphFragment()).commit()
    }
    fun calcDateLong(year: Int, month: Int, day: Int){
        val y = year.toString()
        var m = ""
        var d = ""
        if(month >= 10){
            m = month.toString()
        } else {
            m = "0$month"
        }
        if(day >= 10){
            d = day.toString()
        } else {
            d = "0$day"
        }
        dateLong = (y+m+d).toLong()
    }
    fun calcPer(c: Double, o: Double) {
        val per = round(((c-o)/o)*100*100)/100
        if(per >= 0){
            change_per.setTextColor(Color.RED)
            change_per.text = "(+${per}%)"
        } else{
            change_per.setTextColor(Color.BLUE)
            change_per.text = "(${per}%)"
        }
    }
}