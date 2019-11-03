package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model

data class UserTicket(var id : String, var ticketType: TicketType, var validFrom : String, var validUntil : String)