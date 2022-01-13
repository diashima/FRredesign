package kz.tengrilab.frredesign.data

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("change_password")
    val changePassword: Boolean,
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("is_vip")
    val isVip: Boolean,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("middle_name")
    val middleName: String?,
    val token: String,
    @SerializedName("user_id")
    val userId: Int,
    val username: String
)