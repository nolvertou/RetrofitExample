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
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // TODO (23) In OverViewViewModel, rename response LiveData to status:
    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val status: LiveData<String>
        get() = _status

    // TODO (24) Add an encapsulated LiveData<MarsProperty> property with an internal Mutable and
    //  an external LiveData

    // TODO (32) In OverviewViewModel rename _property to _properties, and assign it a List of MarsProperty
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    // TODO (17) Add variables for a coroutine Job and a CoroutineScope using the Main Dispatcher
    // 17.1 Create Job because we are working with Coroutines
    private var viewModelJob = Job()
    // 17.2 Then we use that job to create a coroutine scope with the main dispatcher, which uses the
    //  UI thread. Since Retrofit does all of its work on a background thread for us, there's no
    //  reason to use any other thread for our scope. This allows us to easily update the value
    //  of our MutableLiveData when we get a result.
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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
        // TODO (18) Replace the enqueue() code with a coroutine for making the API request
        // 18.1 We launch the coroutine that we just created. We're still executing code on the main thread.
        coroutineScope.launch {
            // TODO (19) Inside the coroutine, create a getPropertiesDeferred variable and assign
            //  it a call to getProperties()
            // 19.0 We call MarsAPI our singleton.Mars.getProperties which returns a call object
            // 19.1 Create and starts the network call in a background thread, returning the deferred.
            // 19.2 Calling await on the deferred returns the result from the network call when
            //      the value is ready. Await is nonblocking, which means this will trigger our
            //      API service to retrieve the data from the network without blocking our current
            //      thread, which is important because we´re in the scope of the UI thread.
            var getPropertiesDeferred = MarsApi.retrofitService.getProperties()

            // TODO (20) Add a try/catch block with a call to getPropertiesDeferred.await().
            //  Catch a generic Exception. Save the result from await().
            try {
                // Then return the list size (as before) in the success message
                var listResult = getPropertiesDeferred.await()
                _status.value = "Succes: ${listResult.size} Mars properties retrieved"

                // TODO (25) Update to set _property to the first MarsProperty from listResult
                // TODO (33) Then update getMarsRealEstateProperties() to return the entire list
                //  instead of just one item
                if(listResult.size > 0){
                    _properties.value = listResult
                }

            } catch (e: Exception){
                // Returns the message from the exception in the failure message.
                _status.value = "Failure: ${e.message}"
            }
        }
        _status.value = "Set the Mars API Response here!"
    }
    // TODO (21) override onCleared() and cancel the Job when the ViewModel is finished
    // 21.1 The job is stopped when the OverviewViewModel is destroyed because the overviewFragment
    //      will be gone.
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
