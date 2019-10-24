package hu.bme.aut.temalabor.luciferi.ejegy.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/*
@Entity
data class UserData(
    @PrimaryKey val uid : Int,
    @ColumnInfo(name = "email") val email : String?,
    @ColumnInfo(name = "password") val password : String?
)*/
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "password") val password: String?
)