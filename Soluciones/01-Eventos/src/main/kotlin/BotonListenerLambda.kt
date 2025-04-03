package dev.joseluisgs

class MyButtonLambda {
    // evento onClickListener, que es una función lambda
    private var onClickListener: ((MyButtonLambda) -> Unit)? = null
    
    // Función para asignar un listener al botón
    fun setOnClickListener(listener: (MyButtonLambda) -> Unit) {
        this.onClickListener = listener
    }
    
    // Función para simular el click en el botón
    // Ejecuta el listener, que es un lambda
    fun click() {
        onClickListener?.invoke(this)
    }
}

fun main() {
    // Creamos un botón
    val button = MyButtonLambda()
    
    /*val observador = object {
        fun onClick() {
            println("Button clicked!")
        }
    }*/
    
    // Asignamos un listener al botón
    // Es decir, le decimos que hacer cuando se haga click
    button.setOnClickListener {
        //observador.onClick()  // Ejecutamos el listener en el objeto observador
        println("Button clicked!")  // Ejecutamos el listener en este bloque de código, que es el que se ejecuta cuando se haga click
    }
    
    Thread.sleep(5000)  // Esperamos un segundo para ver el resultado del click en consola
    
    // Simulamos el click en el botón
    println("Simulando click...")  // Mostramos en consola que se está simulando el click del botón
    button.click()
}