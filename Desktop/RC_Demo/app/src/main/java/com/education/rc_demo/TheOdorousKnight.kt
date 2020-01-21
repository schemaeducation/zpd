package com.education.rc_demo

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.convertTo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_assessment.view.*
import kotlinx.android.synthetic.main.story_the_odorous_knight.*

class TheOdorousKnight: AppCompatActivity() {

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.story_the_odorous_knight)
        val textView : TextView = findViewById(R.id.the_odorous_knight_textview_paragraph_1)
        val text: String = getString(R.string.the_odorous_knight_paragraph_1)
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
                textView_snackbar_word.text = "Ridicule"
                textView_snackbar_sample_sentence.text = "To make fun of"
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
        ss.setSpan(fcsBlue, 350, 358, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(clickableSpan,350,358,0)
        ss.setSpan(boldspan, 350, 358, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.setMovementMethod(LinkMovementMethod.getInstance())
        val assessmentButton : Button = findViewById(R.id.button_assessment)
        assessmentButton.setOnClickListener {
            val intent = Intent(this, AssessmentActivity::class.java)
            this.startActivity(intent)
        }
    }
}