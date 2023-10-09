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

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

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
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
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
            Call<String>
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