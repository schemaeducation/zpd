package com.schemaeducation.timerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var initialTime : Long = 0
        var finalTime : Long = 0
        start_time_button.setOnClickListener {
            initialTime = System.nanoTime()
        }
        end_time_button.setOnClickListener {
            finalTime = System.nanoTime()
            time_taken_textView.text = ((finalTime - initialTime)/1000000000).toString()
            finalTime = 0
            initialTime=0
        }
        //time_taken.text = (finalTime - initialTime).toString()
    }
}
