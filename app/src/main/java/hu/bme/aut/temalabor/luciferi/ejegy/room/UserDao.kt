package hu.bme.aut.temalabor.luciferi.ejegy.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
/*
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll() : List<UserData>

    @Insert
    fun add(user: UserData)

    @Delete
    fun delete(user: UserData)
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