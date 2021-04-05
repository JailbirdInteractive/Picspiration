package com.rebirth.picspiration.fragments

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.rebirth.picspiration.adapters.MyPhotoRecyclerViewAdapter
import com.rebirth.picspiration.R
import com.rebirth.picspiration.models.MainViewModel
import com.rebirth.picspiration.models.Photo
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

/**
 * A fragment representing a list of Items.
 */
class PhotoFragment : Fragment() {

    private var columnCount = 1
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.init()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyPhotoRecyclerViewAdapter(viewModel.photos?.value)
                viewModel.photos?.observe(viewLifecycleOwner, Observer<ArrayList<Photo>> {
                    println(it)
                view.adapter?.notifyDataSetChanged()
                })
            }
            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.SECONDS.toMillis(1))
                withContext(Dispatchers.Main) {
                    view.adapter?.notifyDataSetChanged()
                }
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<AppBarLayout>(R.id.appBarLayout)?.visibility=View.VISIBLE

    }
    private suspend fun getDelay(){
        delay(1000)

    }
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}