package com.example.dnail2.Products

class Model(private var name: String,private var price: Int,private var thumbnail: Int){
//    ( var name: String, var price: Int,var thumbnail: Int)
//    private var name: String? = null
//    private var price: Int = 0
//    private var thumbnail: Int = 0


//    fun Model(name: String, price: Int, thumbnail: Int){
//        this.name = name
//        this.price = price
//        this.thumbnail = thumbnail
//    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getprice(): Int {
        return price
    }

    fun setprice(price: Int) {
        this.price = price
    }

    fun getThumbnail(): Int {
        return thumbnail
    }

    fun setThumbnail(thumbnail: Int) {
        this.thumbnail = thumbnail
    }
}