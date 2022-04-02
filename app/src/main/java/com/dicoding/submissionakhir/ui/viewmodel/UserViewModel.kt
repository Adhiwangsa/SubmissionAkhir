package com.dicoding.submissionakhir.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionakhir.database.Configuration
import com.dicoding.submissionakhir.database.data.DataUser
import com.dicoding.submissionakhir.database.model.DetailUserResponse
import com.dicoding.submissionakhir.database.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<DataUser>>()
    val detailUser = MutableLiveData<DetailUserResponse>()

    fun setSearch(username: String){
        Configuration.getApi()
            .getUser(username)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("Failure", t.message!!)
                }
            })
    }

    fun setFollowers(username: String){
        Configuration.getApi()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<DataUser>>{
                override fun onResponse(
                    call: Call<ArrayList<DataUser>>,
                    response: Response<ArrayList<DataUser>>
                ) {
                    if(response.isSuccessful){
                        listUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<DataUser>>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }
    fun setFollowing(username : String) {
        Configuration.getApi()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<DataUser>> {
                override fun onResponse(
                    call: Call<ArrayList<DataUser>>,
                    response: Response<ArrayList<DataUser>>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<DataUser>>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }

    fun setDetail(username: String){
        Configuration.getApi()
            .getDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }

            })
    }

    fun get(): LiveData<ArrayList<DataUser>> = listUser

    fun getDetail(): LiveData<DetailUserResponse> = detailUser
}