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

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// TODO (8) Convert this class to a Kotlin data class that contains properties that match the JSON
//  response fields.
// TODO (9) Use @Json to remap the img_src field to imgSrcUrl in the data class

@Parcelize
data class MarsProperty(
    val id: String?,
    // TO use property names that differ from the attributes in the JSON, we use the @JSON annotation
    // on the property, specifying the actual JSON name inside.
    // Since image source doesn't match out Kotlin style, we'll switch the name of that property in
    // our data class to use ima
    @Json(name = "img_src") val imgSrcUrl: String?,
    val type: String?,
    val price: Double
    // TODO (50): Adding the parcelable interface and implement methods <alt> + <enter> or
    //  use @Parcelize
): Parcelable{
    // TODO (73): Inside the MarsProperty class, create an isRental boolean, and set its value based
    //  on whether the property type is "rent"
    val isRental
        get() = type == "rent"
}
//{
//     Android Studio has made an implementation of the parcelable.creator object for u in the
//     creator Kotlin companion object
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readDouble()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(id)
//        parcel.writeString(imgSrcUrl)
//        parcel.writeString(type)
//        parcel.writeDouble(price)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<MarsProperty> {
//        override fun createFromParcel(parcel: Parcel): MarsProperty {
//            return MarsProperty(parcel)
//        }
//
//        override fun newArray(size: Int): Array<MarsProperty?> {
//            return arrayOfNulls(size)
//        }
//    }
//}


/**
 * @Parcelize is doing the same as <alt>+<enter> implemented members, but its better to use it
 * because if we add or remove properties, we don't have to worry about modifying the parcel
 * functions.
 * It will kee the same efficiency of writing parcel functions by hand but there is no chance that
 * will mess it up and create incorrect objects in crashes.
 * "Tooling for the win"
 */