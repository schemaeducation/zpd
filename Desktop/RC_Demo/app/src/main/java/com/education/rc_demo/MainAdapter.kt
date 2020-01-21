package com.education.rc_demo

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter : RecyclerView.Adapter<CustomViewHolder>() {

    val story_names = arrayOf("Trip to Tanzania", "The Odorous Knight")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder?.view?.textView_story_name.text = story_names.get(position)
        Log.i("abcd", "abcd")
        holder?.story_number = position
    }
}

class CustomViewHolder(val view: View, var story_number :Int = 0):RecyclerView.ViewHolder(view){

    init {
        view.setOnClickListener{
            Log.i("listener", "listener")
            if (story_number == 0){
                val intent = Intent(view.context, TripToTanzania::class.java)
                view.context.startActivity(intent)
            }
            if(story_number == 1){
                val intent = Intent(view.context, TheOdorousKnight::class.java)
                view.context.startActivity(intent)
            }

        }
    }

}