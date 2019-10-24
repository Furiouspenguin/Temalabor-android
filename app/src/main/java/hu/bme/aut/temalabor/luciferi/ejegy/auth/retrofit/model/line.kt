package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model

import com.google.gson.annotations.SerializedName

data class Line(@SerializedName("id") var id : String,
                @SerializedName("number") var number : String,
                @SerializedName("type")var type : String) {
    override fun toString(): String {
        return "id: $id\nnumber: $number\ntype: $type"
    }
}