package com.education.rc_demo

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.story_the_odorous_knight.*

class TripToTanzania: AppCompatActivity(){

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.story_trip_to_tanzania)

        val textView : TextView = findViewById(R.id.trip_to_tanzania_paragraph_3)
        val text: String = getString(R.string.trip_to_tanzania_paragraph_3)
        val ss: SpannableString = SpannableString(text)
        val fcsBlue: ForegroundColorSpan = ForegroundColorSpan(Color.BLUE)
        val boldspan : StyleSpan = StyleSpan(Typeface.BOLD)
        val clickableSpan = object: ClickableSpan(){
            override fun onClick(p0: View) {
                // create the snackbar
                val snackbar : Snackbar = Snackbar.make(scrollView_root_layout, "Sample Text", Snackbar.LENGTH_LONG)
                //get the snackbar's layout view
                var layout : Snackbar.SnackbarLayout = snackbar.view as Snackbar.SnackbarLayout
                // hide the text
                var textView : TextView = layout.findViewById(R.id.snackbar_text)
                textView.visibility = View.INVISIBLE
                // inflate custom view
                val snackView : View = layoutInflater.inflate(R.layout.dictionary_layout, null)
                // configure the view
                val textView_snackbar_word : TextView = snackView.findViewById(R.id.text_word)
                val textView_snackbar_sample_sentence : TextView = snackView.findViewById(R.id.text_sample_sentence)
                textView_snackbar_word.text = "Diverse"
                textView_snackbar_sample_sentence.text = "Something  that shows a great deal of variety"
                layout.setPadding(0,0,0,0)
                layout.addView(snackView,0)
                // chekc the type of view
                if (p0 is TextView){
                    Log.i("textview**", "textview**")
                    // val string : String = p0.text as String
                    Log.i("textview**", p0.text.toString())
                }
                snackbar.show()
            }
        }
        ss.setSpan(fcsBlue, 405, 412, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(clickableSpan,405,412,0)
        ss.setSpan(boldspan, 405, 412, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.setMovementMethod(LinkMovementMethod.getInstance())

        val assessmentButton : Button = findViewById(R.id.button_assessment_trip_to_tanzania)
        assessmentButton.setOnClickListener {
            val intent = Intent(this, TTTAssessmentActivity::class.java)
            this.startActivity(intent)
        }
    }
}