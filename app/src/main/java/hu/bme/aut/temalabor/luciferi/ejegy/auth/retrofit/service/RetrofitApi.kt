package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service

import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApi {

    ////////////////AUTH////////////////

    @POST("/auth/register")
    @FormUrlEncoded
    fun register(
        @Field("name") name : String = "Anonymus",
        @Field("email") email : String,
        @Field("idCard") idCard : String,
        @Field("password") password : String/*,
        @Field("type") type : String = "user"*/
    ) : Call<UserData>

    @POST("/auth/login")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<UserData>

    @POST("/auth/logout")
    fun logout() : Call<ResponseBody>

    @POST("/auth/password")
    @FormUrlEncoded
    fun postPassword(
        @Field("oldPassword") oldPassword : String,
        @Field("newPassword") newPassword : String
    ) : Call<ResponseBody>

    ////////////////////////////////////

    ////////////////USER////////////////

    @GET("/user")
    fun getUser() : Call<UserData>

    @GET("/user/{userId}/tickets")
    fun getUserTickets(@Path("userId") userId : String) : Call<List<UserTicket>>

/*

    @DELETE("/user/{userId}")
    fun deleteUser(@Path("userId") userId : String) : Call<ResponseBody>

    @GET("/user/{userId}")
    fun getUser(@Path("userId") userId : String) : Call<UserData>

    @POST("/user/{userid}")
    @FormUrlEncoded
    fun postUser(
        @Path("userId") userId: String,
        @Field("name") name: String,
        @Field("idCard") idCard: String
    ) : Call<ResponseBody>

    @GET("/users")
    fun getUsers() : Call<List<UserData>>
*/
    ////////////////////////////////////

    ////////////////TICKET////////////////

    @POST("/ticket")
    @FormUrlEncoded
    fun postTicket(
        @Field("name") name: String,
        @Field("validFor") validFor : Int,
        @Field("validTimeUnit") validTimeUnit: String,
        @Field("price") price : Int,
        @Field("line") lineId: String
    ) : Call<ResponseBody>

    @GET("/ticket/{ticketId}")
    fun getTicket(@Path("ticketId") ticketId : String) : Call<TicketTypeWithPrice>

    @POST("/ticket/{ticketId}")
    @FormUrlEncoded
    fun postTicket(
        @Path("ticketId") ticketId : String,
        @Field("name") name: String,
        @Field("validFor") validFor : Int,
        @Field("validTimeUnit") validTimeUnit: String,
        @Field("price") price : Int,
        @Field("line") lineId: String
    ) : Call<ResponseBody>

    @DELETE("/ticket/{ticketId}")
    fun deleteTicket(@Path("ticketId") ticketId : String) : Call<ResponseBody>

    @GET("/ticket/{ticketId}/expiry")
    fun getTicketExpiry(@Path("ticketId") ticketId : String) : Call<TicketTypeWithPrice>

    //specify the start
    @GET("/ticket/{ticketId}/expiry")
    fun getTicketExpiryFrom(
        @Path("ticketId") ticketId : String,
        @Query("start") start : String
    ) : Call<TicketTypeWithPrice>

    @GET("/tickets")
    fun getTickets() : Call<List<TicketTypeWithPrice>>

    @POST("/tickets/buy")
    @FormUrlEncoded
    fun postTicketsBuyFromDate(
        @Field("typeId") typeId: String,
        @Field("fromDate") fromDate: String,
        @Field("count") count: Int = 1
    ) : Call<List<UserTicket>>

    @POST("/tickets/buy")
    @FormUrlEncoded
    fun postTicketsBuy(
        @Field("typeId") typeId: String,
        @Field("count") count: Int = 1
    ) : Call<List<UserTicket>>

    @POST("/tickets/validate")
    @FormUrlEncoded
    fun postTicketsValidate(@Field("vehicleId") vehicleId: String) : Call<ResponseBody>


    @POST("/tickets/inspect")
    @FormUrlEncoded
    fun postTicketsInspect(
        @Field("id") ticketId: String,
        @Field("vehicleId") vehicleId: String
    ) : Call<InspectTicket>

    ////////////////////////////////
/*
    ////////////////TRANSPORT LINE////////////////

    //post a new line
    @POST("/line")
    @FormUrlEncoded
    fun postLine(
        @Field("name") name: String,
        @Field("type") type: String
    ) : Call<ResponseBody>

    @GET("/line/{lineId}")
    fun getLine(@Path("lineId") lineId : String) : Call<Line>

    //change an existing line
    @POST("/line/{lineId}")
    @FormUrlEncoded
    fun postLine(
        @Path("lineId") lineId: String,
        @Field("name") name: String,
        @Field("type") type: String
    ) : Call<ResponseBody>

    @DELETE("/line/{lineId}")
    fun deleteLine(@Path("lineId") lineId: String) : Call<ResponseBody>

    @GET("/lines")
    fun getLines(): Call<List<Line>>

    ////////////////////////////////
*/
/*
    ////////////////VEHICLE////////////////

    //post new vehicle
    @POST("/vehicle")
    @FormUrlEncoded
    fun postVehicle(
        @Field("type") type: String,
        @Field("licencePlate") licencePlate: String,
        @Field("line") line: String //id of the line!
    ) : Call<ResponseBody>

    @GET("/vehicle/{vehicleId}")
    fun getVehicle(@Path("vehicleId") vehicleId : String) : Call<Vehicle>

    //change existing vehicle
    @POST("/vehicle/{vehicleId}")
    @FormUrlEncoded
    fun postVehicle(
        @Path("vehicleId") vehicleId : String,
        @Field("type") type: String,
        @Field("licencePlate") licencePlate: String,
        @Field("line") lineId: String
    ) : Call<ResponseBody>

    @DELETE("/vehicle/{vehicleId}")
    fun deleteVehicle(@Path("vehicleId") vehicleId : String) : Call<ResponseBody>

    @GET("/vehicles")
    fun getVehicles() : Call<List<Vehicle>>

    ////////////////////////////////
*/
}