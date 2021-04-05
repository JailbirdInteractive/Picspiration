package com.rebirth.picspiration.fragments

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.appbar.AppBarLayout
import com.rebirth.picspiration.R
import com.rebirth.picspiration.adapters.MySwipeRecyclerViewAdapter
import com.rebirth.picspiration.models.Photo
import com.rebirth.picspiration.models.SwipeModelFactory
import com.rebirth.picspiration.models.SwipeViewModel
import com.rebirth.picspiration.q
import com.yuyakaido.android.cardstackview.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

/**
 * A fragment representing a list of Items.
 */
class SwipeFragment : Fragment(), CardStackListener {

    private var columnCount = 1
    private var args=""
    lateinit var viewModel: SwipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            args = it.getString(ARG_COLUMN_COUNT)!!
        }

viewModel=ViewModelProviders.of(this,SwipeModelFactory(this.requireActivity().application,args)).get(SwipeViewModel::class.java)
        //viewModel = ViewModelProvider(this).get(SwipeViewModel::class.java)
        viewModel.init()


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_swipe_list, container, false)

        // Set the adapter
        if (view is CardStackView) {
            with(view) {
                val manager: CardStackLayoutManager = CardStackLayoutManager(activity, this@SwipeFragment)
                manager.setVisibleCount(3)
                manager.setStackFrom(StackFrom.Top)
                view.layoutManager = manager
                adapter = MySwipeRecyclerViewAdapter(viewModel.photos?.value)
                viewModel.photos?.observe(viewLifecycleOwner, Observer<ArrayList<Photo>> {
                    println("NEW Item SIZE: ${it.size}")
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

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(args: String) =
                SwipeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_COLUMN_COUNT, args)
                    }
                }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        println("Item Position $position  Item Size: ${viewModel.photos?.value?.size}")

        if (position >= viewModel.photos?.value?.size!! - 5) {
            viewModel.nextPage()
            /*CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.SECONDS.toMillis(1))
                withContext(Dispatchers.Main) {

                }
            }*/

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.init()
        activity?.findViewById<AppBarLayout>(R.id.appBarLayout)?.visibility=View.GONE

    }

    override fun onStop() {
        super.onStop()
        getFragmentManager()?.beginTransaction()?.remove(this)?.commitAllowingStateLoss();

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}