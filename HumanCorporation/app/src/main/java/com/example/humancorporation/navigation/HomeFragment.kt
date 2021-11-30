package com.example.humancorporation.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.humancorporation.MainActivity
import com.example.humancorporation.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
        //inflate란? xml 파일을 메모리에 객체화 시키는 것 -> 즉, xml에 정의된 object들을 kt 파일에서 다룰 수 있게
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_addday.setOnClickListener {
            (activity as MainActivity).toAddFragment()
        }
    }
}