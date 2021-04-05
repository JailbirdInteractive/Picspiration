package com.rebirth.picspiration.repositories

import androidx.lifecycle.MutableLiveData
import com.rebirth.picspiration.models.Photo
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object PhotoRepo {
    //val instance:PhotoRepo= PhotoRepo

    private val data: ArrayList<Photo> = ArrayList()

    fun getPhotos(): MutableLiveData<ArrayList<Photo>> {
        getCats()
        val dataset: MutableLiveData<ArrayList<Photo>> = MutableLiveData()
        dataset.value = data

        return dataset
    }


    fun getCats(): Unit {
        val photos: ArrayList<Photo?> = ArrayList()

        val cats: Array<String> = arrayOf(
                "",
                "Wedding",
                "Black and White",
                "School",
                "Professional",
                "Lingerie",
                "Women",
                "Men",
                "Pets",
                "Beach",
                "Sport",
                "Family",
                "Newborn",
                "Pregnant",
                "Holiday",
                "Summer",
                "Sunset",
                "Night"
        )
        for (cat in cats) {
            (getPhoto(cat))
        }


    }

    private fun getPhoto(query: String) {
        var photo: Photo? = null

        val client = OkHttpClient()

        val request = Request.Builder()
                .url("https://api.pexels.com/v1/search?query=$query portrait")
                .addHeader("Authorization", "563492ad6f917000010000015e2674b19032466e8183a60bfe71ea71")
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        //println("$name: $value")
                    }

                    try {

                        val obj = JSONObject(response.body!!.string())
                        val picArray = obj.getJSONArray("photos")
                        //println(picArray.getJSONObject(0).get("src"))

                        val pic = picArray.getJSONObject(0)
                        val src = pic.getJSONObject("src")
                        photo = Photo(pic.getString("id"), pic.getString("url"), query, pic.getString("photographer_url"), src.getString("original"), src.getString("medium"), src.getString("small"), "",
                                src.getString("large2x"))
                        if (data.contains(photo) == false) {
                            data.add(photo!!)

                        }

                        println("${data.size} photos added")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }


                }
            }

        })


    }
}