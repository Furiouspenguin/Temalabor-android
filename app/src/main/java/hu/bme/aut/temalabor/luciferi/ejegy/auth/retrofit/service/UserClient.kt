package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service

import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.Login
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserClient {

    @POST("login")
    @FormUrlEncoded
    fun simplepostlogin(@Field("email") email : String, @Field("password") password : String
        //@Body login : Login
    ) : Call<ResponseBody>

    @POST("login")
    fun login(@Body login: Login) : Call<User>

    @GET("secretinfo")
    fun getSecret(@Header("Authorization") authToken : String) : Call<ResponseBody>
}


/*

class MainActivity : AppCompatActivity() {
    private var token : String? = null

    val builder = Retrofit.Builder()
        .baseUrl("https://temalabor2019.azurewebsites.net/api/auth/")
        .addConverterFactory(GsonConverterFactory.create())

    val retrofit = builder.build()
    val userClient = retrofit.create(UserClient::class.java)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun simplepostlogin(v : View){
        val retrofit2 = Retrofit.Builder()
            .baseUrl("https://temalabor2019.azurewebsites.net/api/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val client2 = retrofit2.create(UserClient::class.java)

        val call = client2.simplepostlogin("user","user")
        call.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                toastLong("FAILURE")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                toastLong(response.message())
            }

        })
    }



    fun login(v : View){
        val login = Login("user","user")
        val call = userClient.login(login)

        call.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                //Toast.makeText(this@MainActivity,"ERROR",Toast.LENGTH_LONG).show()
                toastLong("ERROR")
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    if (response.body() != null) {
                        toastLong(response.body()!!.token)
                        token = response.body()!!.token
                    } else {
                        toastLong("Empty body")
                    }

                } else {
                    //toastLong("Login not correct")
                    toastLong(response.errorBody()!!.string())
                }
            }

        })
    }

    fun secret(v : View){
        if (token != null) {
            val call = userClient.getSecret(token!!)

            call.enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    toastLong("ERROR")
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        try {
                            if (response.body() != null) {
                                toastLong(response.body()!!.string())
                            } else {
                                toastLong("Empty body")
                            }

                        } catch (e : IOException){
                            e.printStackTrace()
                        }

                    } else {
                        toastLong("Token not correct")
                    }
                }

            })

        } else toastLong("No token available")
    }
}
fun AppCompatActivity.toastLong(s : String){
    Toast.makeText(this,s,Toast.LENGTH_LONG).show()
}

 */