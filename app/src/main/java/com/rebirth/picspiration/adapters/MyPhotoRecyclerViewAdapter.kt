package com.rebirth.picspiration.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rebirth.picspiration.R

import com.rebirth.picspiration.dummy.DummyContent.DummyItem
import com.rebirth.picspiration.fragments.PhotoFragment
import com.rebirth.picspiration.fragments.SwipeFragment
import com.rebirth.picspiration.models.Photo
import com.rebirth.picspiration.q
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPhotoRecyclerViewAdapter(private val values: ArrayList<Photo>?) : RecyclerView.Adapter<MyPhotoRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
       holder.cardText.setText(item?.photog)
        Picasso.get()
            .load(values?.get(position)?.med)
            .into(holder.cardImg)
        holder.cardImg.setOnClickListener( View.OnClickListener {
            //q=item?.photog!!
            println("Photog ${item?.photog}")
           val activity:AppCompatActivity = it.context as AppCompatActivity
            activity.supportFragmentManager
                // 3
                .beginTransaction()
                // 4
                .replace(R.id.container, SwipeFragment.newInstance(item?.photog!!))
                // 5
                .addToBackStack("cards")
                .commit()
        })
    }

    override fun getItemCount(): Int = values?.size!!

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardText: TextView = view.findViewById(R.id.card_text)
        val cardImg: ImageView = view.findViewById(R.id.card_img)

        override fun toString(): String {
            return super.toString() + " '" + cardText.text + "'"
        }
    }
}