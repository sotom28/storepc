package modelo

// Clase sellada Usuario con subclases Admmin y Cliente
sealed class Usuario ( val username:String, val password:String){
    class Admmin (username: String, password: String): Usuario(username, password)
    class Cliente (username: String, password: String): Usuario(username, password)

}


