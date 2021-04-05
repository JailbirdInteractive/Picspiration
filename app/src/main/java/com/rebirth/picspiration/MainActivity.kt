package com.rebirth.picspiration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import com.rebirth.picspiration.fragments.PhotoFragment
import com.rebirth.picspiration.fragments.SwipeFragment
import com.rebirth.picspiration.models.MainViewModel
public var q:String="portrait"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // 2
            supportFragmentManager
                // 3
                .beginTransaction()
                // 4
                .add(R.id.container,PhotoFragment.newInstance(2))
                // 5
                .addToBackStack("search")
                .commit()
        }

        val multiSearchView=findViewById<MultiSearchView>(R.id.search_view)

        val searches:ArrayList<String> = ArrayList()
        multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener{
            override fun onItemSelected(index: Int, s: CharSequence) {
            }

            override fun onTextChanged(index: Int, s: CharSequence) {
            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                supportFragmentManager
                        // 3
                        .beginTransaction()
                        // 4
                        .replace(R.id.container, SwipeFragment.newInstance(s.toString()))
                        // 5
                        .addToBackStack("cards")
                        .commit()
            }

            override fun onSearchItemRemoved(index: Int) {

            }

        })

    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0){
            supportFragmentManager.popBackStackImmediate()

            //supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentByTag("swipe")!!).commit()

        }
        else{
            super.onBackPressed();
        }
        //super.onBackPressed()
    }
}