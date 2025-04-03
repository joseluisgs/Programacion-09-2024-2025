package dev.joseluisgs


interface CafeObserver {
    fun update(message: String)
}

class Cafeteria {
    private val observers = mutableListOf<CafeObserver>()
    
    fun addObserver(observer: CafeObserver) {
        observers.add(observer)
    }
    
    fun removeObserver(observer: CafeObserver) {
        observers.remove(observer)
    }
    
    fun notifyObservers(message: String) {
        for (observer in observers) {
            observer.update(message)
        }
    }
    
    fun serveCoffee() {
        Thread.sleep(2000)  // Simulating coffee preparation time
        println("Preparando el cafe...")
        Thread.sleep(10000) // Simulate time to prepare coffee
        println("Cafe listo!")
        notifyObservers("Ven a por tu café!")
    }
}

class Alumno(val nombre: String) : CafeObserver {
    override fun update(message: String) {
        println("$nombre: $message --> Voy a por mi café!")
    }
}

fun main() {
    val cafeteria = Cafeteria()
    val alumno1 = Alumno("Luis")
    val alumno2 = Alumno("Ana")
    
    println("Soy el alumno: ${alumno1.nombre}")
    println("Soy el alumno: ${alumno2.nombre}")
    
    cafeteria.addObserver(alumno1)
    cafeteria.addObserver(alumno2)
    
    println("Cafetería: ¡El café está en preparación!")
    cafeteria.serveCoffee()
    
    // Remove an observer
    cafeteria.removeObserver(alumno1)
    Thread.sleep(5000)  // Wait for 5 seconds to see the changes
    
    println("Cafetería: ¡El café está en preparación!")
    cafeteria.serveCoffee()
}