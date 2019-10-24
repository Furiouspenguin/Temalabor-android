package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service

import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.Line
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserData
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApi {

    @POST("/auth/register")
    @FormUrlEncoded
    fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("idCard") idCard : String,
        @Field("password") password : String,
        @Field("type") type : String = "user"
    ) : Call<Response>

    //TESZTELVE, JÓ!
    @POST("/auth/login")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<UserData>

    @POST("/auth/logout")
    @FormUrlEncoded
    fun logout() : Call<Response>

    @POST("/auth/password")
    @FormUrlEncoded
    fun postPassword(
        @Field("oldPassword") oldPassword : String,
        @Field("newPassword") newPassword : String
    ) : Call<Response>

    @GET("/user")
    fun getUser() : Call<UserData>

    @GET("/user/{userId}")
    fun getUser(@Path("userId") userId : String) : Call<UserData>

    /*
    postUser(userId)
    deleteUser(userId)
    getUserTickets(userId)
     */

    @GET("/users")
    fun getUsers() : Call<List<UserData>>

    /*
    postTicket()
    getTicket(tickedId)
    postTicket(ticketId)
    deleteTicket(ticketId)
    getTickets()
    postTicketsBuy()
    postTicketsValidate()
    postTicketsInspect()
     */

    /*
    postLine()
    getLine(lineId)
    postLine(lineId)
    deleteLine(lineId)
     */

    @GET("/lines")
    fun getLines(): Call<List<Line>>

    /*
    postVehicle()
    getVehicle(vehicleId)
    postVehicle(vehicleId)
    deleteVehicle(vehicleId)
    getVehicles()
     */
}