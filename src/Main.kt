package storepc

import modelo.Inventario
import modelo.Producto
import modelo.Usuario


fun main (){

    val inventario = Inventario().apply { seedDatos()}

    // usuario predefinido
    val admin = Usuario.Admmin("admin", "admin123") // usuario admin
    val cliente =  Usuario.Cliente ("cliente", "cliente123") // usuario cliente

    println("=== gestion de usuarios===")

    println("Usuario :") // mostrar usuario admin
    val userInput = readLine()?.trim() // leer entrada del usuario

    print("Password:")
    val passwordInput = readLine()?.trim()

    val usuario: Usuario = when {
        userInput == admin.username && passwordInput == admin.password -> admin // escribir usuario y contraseña admin
        userInput == cliente.username && passwordInput == cliente.password -> cliente // escribir usuario y contraseña cliente

        else -> {
            println("Usuario o contraseña incorrectos")
            return
        }
    }

    println("Bienvenido, ${usuario.username}!\n")
    /// Menú principal
    loop@ while (true){
        if(usuario is Usuario.Admmin) {
            println(
                """
            menu admin:
            1) listarProducto
            2) agregar Productos
            3) Actualizar Producto
            4) Eliminar Producto
            5) Sumar Stock
            6) Restar Stock(restar stock)
            7) reporte bajo de stock
            0) Salir

            """.trimIndent() // mostrar menú admin
            )
        }else if (usuario is Usuario.Cliente){
        println(
            """
            menu cliente:
            1) listarProducto
            2) comprar Producto(restar stock)
            0) Salir
            """.trimIndent() // mostrar menú cliente
            )
        }

        print("Seleccione una opción: ")
        val opt = readLine()?.trim()
        try{
            when (opt) {
                // listar productos (ambos usuarios)
                "1" -> {
                    // usar el método existente de inventario
                    val lista = inventario.listarProductos()
                    if (lista.isEmpty()) println("No hay productos en el inventario.")
                    else lista.forEach { println(it) }
                }
                /// Agregar Producto (solo admin)
                "2" -> {
                    if (usuario is Usuario.Admmin) {
                        print("ID:");
                        val id = readLine()!!.toInt()
                        print("Nombre:");
                        val nombre = readLine()!!.toString()
                        print("Descripcion:");
                        val descripcion = readLine()!!.toString()
                        print("Precio:");
                        val precio = readLine()!!.toDouble()
                        print("Stock:");
                        val stock = readLine()!!.toInt()
                        print("Categoria:");
                        val categoria = readLine()!!.toString()
                        val nuevoProducto = Producto(id, nombre, descripcion, precio, stock, categoria)
                        inventario.agregarProducto(nuevoProducto)
                        println("Producto agregado exitosamente.")

                    } else {
                        print("ID del producto a comprar:");
                        val id = readLine()!!.toInt()
                        print("Cantidad a comprar:");
                        val cantidad = readLine()!!.toInt()
                        try {
                            inventario.restarStock(id, cantidad)
                            println("Compra realizada exitosamente.")
                        } catch (e: Exception) {
                            println("Error al comprar producto: ${e.message}")
                        }
                    }

                }
                // Actualizar Producto (solo admin)
                "3" -> {
                    if (usuario is Usuario.Admmin) {
                        print("ID a actualizar: ");
                        val id = readLine()!!.toInt()
                        print("Nuevo nombre (enter para omitir): ")
                        val nombre = readLine()?.takeIf { it.isNotBlank() }?.trim()
                        print("Nuevo precio (enter para omitir): ")
                        val precioInput = readLine()?.takeIf { it.isNotBlank() }?.trim()
                        print("Nueva descripcion (enter para omitir): ")
                        val descInput = readLine()?.takeIf { it.isNotBlank() }?.trim()
                        print("Nuevo stock (enter para omitir): ")
                        val stockInput = readLine()?.takeIf { it.isNotBlank() }?.trim()

                        val precio = precioInput?.toDouble()
                        val nuevoStock = stockInput?.toInt()


                        try {
                            inventario.actualizarProducto(id, nombre, precio, descInput, nuevoStock, null)
                            println("Producto actualizado exitosamente.")
                        } catch (e: NoSuchElementException) {
                            println("Producto no encontrado.")
                        }
                    } else println("Opción inválida.");
                }
                // Eliminar Producto (solo admin)
                "4" -> {
                    if (usuario is Usuario.Admmin) {
                        print("ID a eliminar:");
                        val id = readLine()!!.toInt()
                        try {
                            inventario.eliminarProducto(id)
                            println("Eliminado exitosamente.")
                        } catch (e: NoSuchElementException) {
                            println("Producto no encontrado.")
                        }
                    } else println("opción inválida.")

                }
                // Sumar Stock (solo admin)
                "5" -> {
                    if (usuario is Usuario.Admmin) {
                        print("ID del producto:");
                        val id = readLine()!!.toInt()
                        print("Cantidad a sumar:");
                        val cantidad = readLine()!!.toInt()
                        try {
                            inventario.sumarStock(id, cantidad)
                            println("Stock actualizado exitosamente.")
                        } catch (e: Exception) {
                            println("Error al sumar stock: ${e.message}")
                        }
                    } else println("opción inválida.")
                }
                /// Restar Stock (soloadmin)
                "6" -> {
                    if (usuario is Usuario.Admmin) {
                        print("ID del producto:");
                        val id = readLine()!!.toInt()
                        print("Cantidad a restar:");
                        val cantidad = readLine()!!.toInt()
                        try {
                            inventario.restarStock(id, cantidad)
                            println("Stock actualizado exitosamente.")
                        } catch (e: Exception) {
                            println("Error al restar stock: ${e.message}")
                        }
                    } else println("opción inválida.")
                }
                // reporte bajo de stock (solo admin)
                "7" -> {
                    if (usuario is Usuario.Admmin) {
                        val reporte = inventario.productosBajoStock()
                        if (reporte.isEmpty()) println("No hay reporte")
                        else {
                            println("Productos con bajo stock:")
                            reporte.forEach { println(it) }
                        }
                    } else println("opción inválida.")
                }

                "0" -> {
                    println("Saliendo del sistema. ¡Hasta luego!")
                    break@loop
                }

                else -> println("Opción inválida. Por favor, intente de nuevo.")
            }
        } catch (e: NumberFormatException) {
            println("Entrada inválida. Por favor, ingrese un número válido.")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
            println()

                }
            }

