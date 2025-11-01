
package modelo
import modelo.Producto

import kotlin.random.Random




/// Clase Inventario para gestionar productos en el inventario
class Inventario {

    private val productos: MutableList<Producto> = mutableListOf()

    // Agregar un producto al inventario
    fun agregarProducto(producto: Producto) {
                if (buscarProducto(producto.id) == null) {
                    productos.add(producto)
                } else {
                    throw IllegalArgumentException("El producto con ID ${producto.id} ya existe.")
            }
        }

    // buscar un producto por su ID
    fun buscarProducto(id: Int): Producto? = productos.find { it.id == id }


    ///  listar todos los productos
    fun listarProductos(): List<Producto> = productos.toList()

    /// editar un producto existente
        fun actualizarProducto(
        id: Int,
        nuevoNombre: String?,
        nuevoPrecio: Double?,
        nuevoDescripcion: String?,
        nuevoStock: Int?,
        Nuevacategoria: String?
    ) {
        val p = buscarProducto(id) ?: throw NoSuchElementException("Producto no encontrado")
        nuevoNombre?.let { p.nombre = it } // Actualiza el nombre si no es nulo
        nuevoPrecio?.let { p.precio = it } // Actualiza el precio si no es nulo
        nuevoDescripcion?.let { p.descripcion = it } // Actualiza la descripción si no es nulo
        nuevoStock?.let { p.stock = it }
        Nuevacategoria?.let { p.categoria = it }


    }
    // eliminar un producto del inventario
    fun eliminarProducto(id: Int) {
        val producto = buscarProducto(id) ?: throw NoSuchElementException("Producto no encontrado")
        productos.remove(producto)
    }
    /// sumar cantidad de stock a lo productos existente
    fun sumarStock(id: Int, cantidad: Int) {
        if(cantidad <= 0) throw IllegalArgumentException("La cantidad a sumar debe ser mayor que cero")
                val p = buscarProducto(id) ?: throw NoSuchElementException("Producto no encontrado")
        p.stock += cantidad
    }


    //restar stock
    fun restarStock(id: Int, cantidad: Int) {
        val p = buscarProducto(id) ?: throw NoSuchElementException("Producto no encontrado")
                if (cantidad <= 0) throw IllegalArgumentException ("La cantidad a restar debe ser mayor que cero")
        if (p.stock < cantidad) throw IllegalArgumentException ("No hay suficiente stock para restar")
        p.stock -= cantidad
    }


    //reporte bajo de stock de productos
    fun productosBajoStock(umbral: Int=2): List<Producto> {
        return productos.filter { it.stock < umbral }
    }


    // Ejemplo de uso de map para presentar reportes simples
    fun reporteBajoStockTexto(umbral: Int = 5): List<String> =
        productosBajoStock(umbral).map { "${it.nombre} (ID:${it.id}) - Stock:${it.stock}" }

    // Datos iniciales opcionales // para pruebas
    fun seedDatos() {
        productos.addAll(
            listOf(
                Producto(1, "procesador intel i5 14600", "procesador 14 generacion ", 299.99, 5, "procesadores"),
                Producto(2, "memoria ram 16gb", "memoria ram ddr4", 79.99, 10, "memorias"),
                Producto(3, "disco solido 1tb", "disco solido ssd", 119.99, 50, "almacenamiento"),
                Producto(4, "tarjeta grafica gtx 1660", "tarjeta grafica para juegos", 249.99, 12, "tarjetas graficas"),
                Producto(5, "monitor 24 pulgadas", "monitor full hd", 149.99, 10, "monitores"),
                Producto(6, "fuente de poder 600w", "fuente de poder certificada BRONZE", 89.99, 15, "fuentes de poder")

            )
        )
    }
}
