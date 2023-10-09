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

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"


// TODO (10) Use the Moshi Builder to create a Moshi Object with the KotlinJsonAdapterFactory
// 10.1 We need to add the Kotlin JSON adapter factory, then we build our Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// TODO (02) Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
// 2.1 We use Retrofit.Builder to fetch a JSON Response from our web service and return it as a string
// 2.2 We use ScalarsConverterFactory that supports returning strings and other primitive types so we call
//     addConverterFactory in the builder with the instance of the ScalarsCoverterFactory
// 2.3 Then we call BASE_URL to specify the route web address of our server endpoints
// 2.4 Finally we call build to create the retrofit object
/**
 * Add an instance of ScalarsConverterFactory and the BASE_URL we provided, then call build()
 * to create the Retrofit object.
 */
// TODO (11) Change the ConverterFactory to the MoshiConverterFactory with our Moshi Object/
// 11.1 We'll let retrofit know that it can use Moshi to convert the JSON response into Kotlin objects.
//      So, we can then delete the ScalarsConverterFactory since we won't need it any longer and
//      remove
// TODO (15)  Add a CoroutineCallAdapterFactory to the Retrofit builder to enable Retrofit to produce
//  a coroutines-based API
// 15.1 CallAdapters add the ability for Retrofit to create APIs that return something other
//      than the default call class.
// 15.2 In this case, the CoroutineCallAdapterFactory allows us to replace the call and get properties
//      with a coroutine deferred.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

// TODO (03) Implement the MarsApiService interface with @GET getProperties returning a String
// Next, we'll define an interface that explains how retrofit talks to our web server using HTTP requests
// Retrofit, will create an object that implements our interface with all of the methods that talk
// to the server
// 3.1 MarsApiService defines the API our retrofit service creates
// 3.2 Get the JSON response string
/**
 * Create a MarsApiService interface, and define a getProperties() method to request
 * the JSON response string.
 * Annotate the method with @GET, specifying the endpoint for the JSON real estate response,
 * and create the Retrofit Call object that will start the HTTP request.
 */
interface MarsApiService{
    // 3.3 Specify in the annotation the path or endpoint of the JSON resonse
    // that we want this method to use.
    // e.g. Retrofit append to the endpoint realstate to the BASE_URL and creates the call object
    @GET("realestate")
    // 3.4 Call object is used to start the request and create a retofit service
    fun getProperties():
            // TODO (12) Update the MarsApiService to return a List of MarsProperty Objects
            // TODO (16) We can replace the call return value and getProperties with a deferred
            //  from coroutines
            // 16.1 Deferred value is a non-blocking cancellable future - It is a Job that has a result.
            // 16.2 As a reminder, a coroutine jobe provides a way of cancelling and determining the
            //  state of a coroutine
            // 16.3 But unlike a job, deferred has a method called await. Await is a suspend function
            //  on the deferred. It causes the code to await without blocking in true coroutines fashion
            //  fashion until the value is ready and then the value is return.
            // 16.4 Retrofit will return a deferred and then you await the result which has the
            //  appearance o synchronous code. And if there is an error, await will return that
            //  throwing an exception
            Deferred<List<MarsProperty>>
}

// TODO (04) Create the MarsApi object using Retrofit to implement the MarsApiService
// Since this retrofit create calls is expensive and our app only needs one retrofit service instance,
// we'll expose the rest of the application using a public object called MarsApi
object MarsApi{
    // 4.1 In MarsApi object we'll add a lazily initialized retrofit object propertly named
    // retrofitService, which gets initialized using retrofit.create method with out MarsApiService
    // Interface
    val retrofitService: MarsApiService by lazy{
        retrofit.create(MarsApiService::class.java)
    }
}