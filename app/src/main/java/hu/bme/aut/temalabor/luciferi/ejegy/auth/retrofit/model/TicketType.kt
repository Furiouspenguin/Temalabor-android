package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model

data class TicketType(var name : String = "anonymus", var type : String, var typeId : String, var validFor : Int, var validTimeUnit : String, var line : Line)