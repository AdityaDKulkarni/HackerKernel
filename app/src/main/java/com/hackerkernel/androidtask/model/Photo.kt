package com.hackerkernel.androidtask.model

data class Photo(val albumId: Int, val id: Int, val title: String, val url: String, val thumbnailUrl: String){
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Data

        if (id != other.id) return false

        return true
    }
}