package com.example.bimbam

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class UserViewModel : ViewModel() {
    private val userData = MutableLiveData<UserData>()

    fun setUserData(user: UserData) {
        userData.value = user
    }

    fun getUserData(): LiveData<UserData> {
        return userData
    }

}
