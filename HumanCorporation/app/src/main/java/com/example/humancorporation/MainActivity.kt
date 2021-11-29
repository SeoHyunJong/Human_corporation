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
            setOnItemSelectedListener { item->
                when(item.itemId){
                    R.id.action_home -> {
                        var homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, homeFragment).commit()
                    }
                    R.id.action_graph -> {
                        var graphFragment = GraphFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, graphFragment).commit()

                    }
                    R.id.action_add -> {
                        var addFragment = AddFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, addFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.action_home
        }

    }
}