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

package com.example.android.marsrealestate

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// TODO (27) Create the Binding Adapter, converting the imgUrl to a URI with the https scheme
// 27.1 We're going to build a binding adapter that will take the URL from an XML attribute
//      associated with aniImgView and use Glide to load the image. BindingAdapter annotation
//      tells Data Binding that we want this Binding Adapter executed when an XML item has the
//      imageUrl attribute.
// 27.2 Next, we implement the BindingAdapter function. The view parameter is specified as an
//      ImageView, which means that only ImageView and any derived classes can use this adapter.
//      It also has the imgUrl passed in as string
// 27.3 We'll wrap the code in a "let" block to handle null URIs
// 27.4 Glide requires an URI object, so inside the adapter we'll begin by converting the imgUrl
//      to a URI.
// 27.5 We're going to make sure that the resulting URI has the HTTPS scheme, because the server
//      we're pulling the images from requires HTTPS
// 27.6 Glide has a fluent interface which mean that it uses chained calls with a readable syntax.
//      to load an image with Glide, we cal Glide.with ...
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
                // TODO (31) We apply a new RequestOptions object and specify a placeholder image
                //  to use a place holder image to use while loading or unloading animation drawable
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation))
            .into(imgView)
    }
}
