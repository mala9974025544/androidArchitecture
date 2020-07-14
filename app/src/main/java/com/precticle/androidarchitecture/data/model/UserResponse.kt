package com.precticle.androidarchitecture.data.model

import com.google.gson.annotations.SerializedName

class UserResponse {
    @field:SerializedName("data")
    var users: List<User>? = null
    @field:SerializedName("page")
    var page: Int = 0
    @field:SerializedName("per_page")
    var perPage: Long = 0
    @field:SerializedName("total")
    var total: Long = 0
    @field:SerializedName("total_pages")
    var totalPages: Int = 0
}