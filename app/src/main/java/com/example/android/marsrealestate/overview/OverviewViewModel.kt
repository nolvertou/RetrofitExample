/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiService
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _response

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {
        // TODO (05) Call the MarsApi to enqueue the Retrofit request, implementing the callbacks
        // 5.1 We call MarsAPI our singleton.Mars.getProperties which returns a call object
        // 5.2 We then call enqueue on that callback to start the network request on a background thread
        //     The enqueue method takes a retrofit callback class input that contains methods
        //     that will be called when the request is complete.
        //     The callback has both, success and failure methods, which are called accordingly when
        //     retrofit is successful in fetching the JSON or an error occurs. So lets defin a Kotlin
        //     object to implement this callback
        // 5.3 I finish creating the object by pressing <CTRL><i> as a shortcut to implement methods
        //      within Android Studio.
        MarsApi.retrofitService.getProperties().enqueue(object: retrofit2.Callback<String>{

            // 5.4 The _response is a string live data that determines what's shown in the TextView
            //     Each states needs to update the _response live data
            //     For onResponse, we have successfully gotten a response from our server.
            //     So, we get the value using response.body()
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body()

            }

            // 5.5 For onFailure, we'll set response to failure plus the message from the throwable.
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

        })


        _response.value = "Set the Mars API Response here __!"
    }
}
