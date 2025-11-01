package modelo




data class Producto(
    val id: Int,
    var nombre: String,
    var descripcion: String,
    var precio: Double,
    var stock: Int,
    var categoria: String

)
{
    override fun toString(): String {
        return "Producto(id=$id, nombre='$nombre', descripcion='$descripcion', precio=$precio, stock=$stock, categoria='$categoria')"
    }


}
