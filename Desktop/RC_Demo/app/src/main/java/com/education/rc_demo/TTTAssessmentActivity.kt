package com.education.rc_demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TTTAssessmentActivity : AppCompatActivity() {
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment_tts)
        // get the hint button here:
        val trip_to_tanzania_hint_button : Button = findViewById(R.id.trip_to_tanzania_hint_button)
        trip_to_tanzania_hint_button.setOnClickListener {
            // The idea here is to alter the Trip To Tanzania paragraph 3
            // So to do this, we have to get the spannable object like we did before, this can be done outside
            // the button press.

            val intent = Intent(this, AssessmentActivity::class.java)
            this.startActivity(intent)
        }
    }
    }
}