package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model

data class UserData(var id : String, var email : String, var name : String, var idCard : String, var type : String) {
    override fun toString(): String {
        return "ID: $id\nEmail: $email\nName: $name\nidCard: $idCard\nType: $type"
    }
}