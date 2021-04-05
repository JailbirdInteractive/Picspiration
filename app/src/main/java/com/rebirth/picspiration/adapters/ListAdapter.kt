package com.rebirth.picspiration.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.rebirth.picspiration.R
import com.rebirth.picspiration.models.Photo
import com.rishabhharit.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class ListAdapter(private val dataSet: ArrayList<Photo?>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView:ImageView
        val shareBtn:ImageView
        val picButton:RoundedImageView
        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.title_card)
            imageView=view.findViewById(R.id.picture_card)
            shareBtn=view.findViewById(R.id.shr_btn)
            picButton=view.findViewById(R.id.round_pic)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.textView.text = dataSet[position]?.photog

        Picasso.get()
            .load(dataSet[position]?.large)
            //.resize(1080,1920)
            //.centerInside()
            .into(viewHolder.imageView)
        Picasso.get()
            .load(dataSet[position]?.small)
            //.resize(1080,1920)
            //.centerInside()
            .into(viewHolder.picButton)
        viewHolder.shareBtn.setOnClickListener {

            val i = Intent(Intent.ACTION_VIEW, Uri.parse(dataSet[position]?.url))
            startActivity(it.context,i,null)

        }
        viewHolder.picButton.setOnClickListener {

            val i = Intent(Intent.ACTION_VIEW, Uri.parse(dataSet[position]?.photogUrl))
            startActivity(it.context,i,null)

        }
        }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



}
