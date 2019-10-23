package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model

data class Ticket(var type : String, var typeId : String, var validFor : Int, var line : Line, var price : Int)