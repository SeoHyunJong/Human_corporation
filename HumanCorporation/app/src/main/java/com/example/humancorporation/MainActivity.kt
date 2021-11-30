package com.example.humancorporation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.humancorporation.navigation.AddFragment
import com.example.humancorporation.navigation.GraphFragment
import com.example.humancorporation.navigation.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val addFragment = AddFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_content, addFragment).commit()
    }
}