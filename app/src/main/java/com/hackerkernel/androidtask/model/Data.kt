package com.hackerkernel.androidtask.model

data class Data(val id: Int, val api_token: String, val name: String, val email: String, val mobile: String,
                val outlets: Array<String>?, val email_verified_at: String, val user_type: String, val sales_type: String?,
                val wapicash_transfer: String?, val created_at: String, val updated_at: String?, val deleted_at: String?, val time: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}