package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model

import com.google.gson.annotations.SerializedName

data class Line(@SerializedName("id") var id : String,
                @SerializedName("name") var name : String,
                @SerializedName("type")var type : String) {
    override fun toString(): String {
        return "id: $id\nnumber: $name\ntype: $type"
    }
}