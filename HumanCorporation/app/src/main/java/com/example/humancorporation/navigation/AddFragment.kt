package com.example.humancorporation.navigation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.humancorporation.AppDatabase
import com.example.humancorporation.MainActivity
import com.example.humancorporation.R
import kotlinx.android.synthetic.main.fragment_add.*
import java.time.LocalDate
import java.util.*
import im.dacer.androidcharts.ClockPieHelper

import im.dacer.androidcharts.ClockPieView
import kotlin.math.abs

class AddFragment : Fragment(){
    var startTime:Int = 0
    var startHour:Int = 0
    var startMinute:Int = 0
    var endTime:Int = 0
    var dateLong: Long = 0
    var clockPieHelperArrayList = ArrayList<ClockPieHelper>()
    var remain: Int = 1440

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DefaultDate()
        DefaultClock()
        DefaultRemain()
        btn_complete.setOnClickListener {
            completeAction(dateLong)
            Toast.makeText(activity, "성공적으로 기록되었습니다.", Toast.LENGTH_SHORT).show()
        }
        btn_date.setOnClickListener {
            setDate()
        }
        start_time.setOnClickListener{
            getTime(start_time)
        }
        end_time.setOnClickListener {
            getTime(end_time)
        }
        btn_add.setOnClickListener {
            productiveAction(1)
        }
        btn_minus.setOnClickListener{
            productiveAction(2)
        }
        btn_neutral.setOnClickListener {
            productiveAction(3)
        }
        deleteAll.setOnClickListener {
            deleteAction(dateLong)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun DefaultDate() {
        val date = LocalDate.now() //현재의 날짜 정보를 받아온다
        calcDateLong(date.year, date.monthValue, date.dayOfMonth)
        btn_date.text = "${date.year}.${date.monthValue}.${date.dayOfMonth}"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate() {
        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            calcDateLong(y, m+1, d)
            btn_date.text = "${y}.${m+1}.${d}"
            clearGraph()
            DefaultClock()
            remain = 1440
            DefaultRemain()
        }
        DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        //requireContext: 이 fragment와 관련된 context를 반환해준다.
        //2번째에 R.style.MySpinnerDatePickerStyle 을 넣어주면 spinner 형식이 된다.
    }
    fun getTime(button: Button){

        val cal = Calendar.getInstance() // 캘린더 객체 생성

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            if(button == start_time){
                startHour = hour
                startMinute = minute
                if(minute >= 10) {
                    startTime = "${hour}${minute}".toInt()
                } else {
                    startTime = "${hour}0${minute}".toInt()
                }
                button.text = "${hour}시 ${minute}분"
            } else if(start_time.text != "시작 시간"){
                if(hour > startHour || (hour == startHour && minute > startMinute)){
                    if(minute >= 10) {
                        endTime = "${hour}${minute}".toInt()
                    } else {
                        endTime = "${hour}0${minute}".toInt()
                    }
                    button.text = "${hour}시 ${minute}분"
                } else {
                    Toast.makeText(activity, "종료시간은 당일 기준 시작시간보다 커야 합니다.", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(activity, "시작 시간을 먼저 정해야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }
    /*앞으로 해야 할 일
    1. database 구축
    2. ClockPieView 채우기
    3. Graph Fragment 완성
    4. 최종완성 및 발표준비
     */
    fun productiveAction(case: Int) {
        if(startTime < endTime) {
            if((activity as MainActivity).AddtoDB(dateLong, startTime, endTime, editDay.text.toString(), case)){
                setClock(startTime, endTime)
                remainAction()
            }
        } else {
            Toast.makeText(activity, "입력 시간을 다시한번 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAction(d: Long){
        (activity as MainActivity).DeleteDB(d)
    }

    private fun setClock(start: Int, end: Int) {
        var sH: Int = 0
        var sM: Int = 0
        var eH: Int = 0
        var eM: Int = 0

        if(start >= 100){
            sH = start / 100
            sM = start % 100
        } else{
            sH = 0
            sM = start
        }
        if(end >= 100){
            eH = end / 100
            eM = end % 100
        } else{
            eH = 0
            eM = end
        }
        clockPieHelperArrayList.add(ClockPieHelper(sH, sM, eH, eM))
        clock_pie_view.setDate(clockPieHelperArrayList)
    }

    fun DefaultClock(){
        val lst = (activity as MainActivity).getDB(dateLong)
        if (lst != null) {
            for(item in lst){
                setClock(item.startTime, item.endTime)
            }
        }
    }

    fun clearGraph(){
        clockPieHelperArrayList = ArrayList<ClockPieHelper>()
        clockPieHelperArrayList.add(ClockPieHelper(0, 0, 0, 1))
        clock_pie_view.setDate(clockPieHelperArrayList)
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

    fun completeAction(d: Long){
        (activity as MainActivity).completeDay(d)
    }

    fun DefaultRemain(){
        val lst = (activity as MainActivity).getDB(dateLong)
        if (lst != null) {
            for(item in lst){
                val minute = ((item.endTime/100)*60 + item.endTime%100) - ((item.startTime/100)*60 + item.startTime%100)
                remain -= minute
            }
            remainMinute.text = "${remain}분"
        }
    }

    fun remainAction(){
        val minute = ((endTime/100)*60 + endTime%100) - ((startTime/100)*60 + startTime%100)
        remain -= minute
        remainMinute.text = "${remain}분"
    }
}