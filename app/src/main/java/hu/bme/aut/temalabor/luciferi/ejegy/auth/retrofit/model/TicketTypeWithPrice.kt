package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model

data class TicketTypeWithPrice(var name : String,
                               var type : String,
                               var typeId : String,
                               var validFor : Int,
                               var validTimeUnit : String,
                               var line : Line,
                               var price : Int)