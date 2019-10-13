package hu.bme.aut.temalabor.luciferi.ejegy.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
/*
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll() : List<User>

    @Insert
    fun add(user: User)

    @Delete
    fun delete(user: User)
}*/
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun add(user: User)

    @Delete
    fun delete(user: User)
}