package com.example.dnail2.Times

class Time(private var time: String) {

    fun Time(time: String) {
        this.time = time
    }

    fun getTime(): String? {
        return time
    }

    fun setTime(time: String) {
        this.time = time
    }
}