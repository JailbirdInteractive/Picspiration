package com.rebirth.picspiration.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rebirth.picspiration.R

import com.rebirth.picspiration.dummy.DummyContent.DummyItem
import com.rebirth.picspiration.models.Photo
import com.rishabhharit.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MySwipeRecyclerViewAdapter(
    private val values: List<Photo>?
) : RecyclerView.Adapter<MySwipeRecyclerViewAdapter.ViewHolder>() {

    init {
        // Define click listener for the ViewHolder's View.

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_swipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        Picasso.get()
            .load(item?.large)
            //.resize(1080,1920)
            //.centerInside()
            .into(holder.imageView)
        Picasso.get()
            .load(item?.small)
            //.resize(1080,1920)
            //.centerInside()
            .into(holder.picButton)
    }

    override fun getItemCount(): Int = values?.size!!

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView = view.findViewById<TextView>(R.id.title_card)
        val imageView= view.findViewById<ImageView>(R.id.picture_card)
       val  shareBtn=  view.findViewById<ImageView>(R.id.shr_btn)
        val picButton= view.findViewById<ImageView>(R.id.round_pic)

        override fun toString(): String {
            return super.toString() + " '" +  "'"
        }
    }
}