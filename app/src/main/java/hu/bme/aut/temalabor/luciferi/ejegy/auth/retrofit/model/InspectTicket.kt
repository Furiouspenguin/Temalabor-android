package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model

//data class InspectTicket(var status : String, var validFrom : String, var validUntil : String, var line : String)
data class InspectTicket(var status : String, var user : UserData, var tickets : List<UserTicket>)