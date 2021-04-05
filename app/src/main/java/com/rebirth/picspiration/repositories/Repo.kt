package com.rebirth.picspiration.repositories

import androidx.lifecycle.MutableLiveData
import com.rebirth.picspiration.models.Photo
import com.rebirth.picspiration.q
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class Repo(var item:String) {
    private val data: ArrayList<Photo> = ArrayList()
    private var url:String="https://api.pexels.com/v1/search?query="
    private var nextUrl=""

    fun getPhotos(): MutableLiveData<ArrayList<Photo>> {
        println("ITEM $item")
        getUrls(item)
        val dataset: MutableLiveData<ArrayList<Photo>> = MutableLiveData()
        dataset.value = data

        return dataset
    }
    fun getMorePhotos(): MutableLiveData<ArrayList<Photo>> {
        getNextPage("")
        val dataset: MutableLiveData<ArrayList<Photo>> = MutableLiveData()
        dataset.value = data

        return dataset
    }

    private fun getUrls(query: String) {
        val client = OkHttpClient()
        data.clear()
        var fullUrl="$url$query portraits&per_page=25"

      /*  if(nextUrl.isNullOrBlank()){
            fullUrl="$url$query&per_page=25"
        }else{
            fullUrl= nextUrl
        }*/
        val request = Request.Builder()
                .url(fullUrl)
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
                        println("$name: $value")
                    }

                    //println(response.body!!.string())
                    try {

                        val obj = JSONObject(response.body!!.string())
                        val picArray = obj.getJSONArray("photos")
                        //var src=picArray.("src")
                        //println(picArray.getJSONObject(0).get("src"))
                        for (i in 0 until picArray.length()) {
                            val pic = picArray.getJSONObject(i)
                            val src = pic.getJSONObject("src")
                            var photo = Photo(pic.getString("id"), pic.getString("url"), pic.getString("photographer"), pic.getString("photographer_url"), src.getString("original"), src.getString("medium"), src.getString("small"), obj.getString("next_page"), src.getString("large"))
                            if (!data.contains(photo))
                                data.add(photo)

                        }
                        nextUrl=data?.get(0).nextPage
                        //url= data.get(0).nextPage
                        //println("PHOTO ARRAY: ${obj.getString("next_page")}")

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }


                }
            }
        })

    }
   private fun getNextPage(query: String) {
        val client = OkHttpClient()
        //data.clear()
        var fullUrl=nextUrl

        val request = Request.Builder()
                .url(fullUrl)
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
                        println("$name: $value")
                    }

                    //println(response.body!!.string())
                    try {

                        val obj = JSONObject(response.body!!.string())
                        val picArray = obj.getJSONArray("photos")
                        //var src=picArray.("src")
                        //println(picArray.getJSONObject(0).get("src"))
                        for (i in 0 until picArray.length()) {
                            val pic = picArray.getJSONObject(i)
                            val src = pic.getJSONObject("src")
                            var photo = Photo(pic.getString("id"), pic.getString("url"), pic.getString("photographer"), pic.getString("photographer_url"), src.getString("original"), src.getString("medium"), src.getString("small"), obj.getString("next_page"), src.getString("large"))
                            if (!data.contains(photo))
                                data.add(photo)

                        }
                        nextUrl=data.get(data.size-1).nextPage
                        //url= data.get(0).nextPage
                        //println("PHOTO ARRAY: ${obj.getString("next_page")}")

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }


                }
            }
        })

    }
}