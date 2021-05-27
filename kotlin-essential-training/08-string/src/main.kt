fun main(args: Array<String>){
    var aString = "Hola"
    println(aString)

    var vacio = String()
    println("'$vacio'")

    var charArray = aString.toCharArray()
    println(charArray.toList())

    var byteArray = aString.toByteArray()
    println(byteArray.toList())

    aString += "Bienvenido"
    println(aString)

    val len = aString.length
    for (i in 0 until len) {
        val c = aString.get(i)
        println(c)
    }

    val p =aString.indexOf("B")
    val sub = aString.substring(p)
    println(sub)

    val string2 = aString.toUpperCase()
    val match = aString.equals(string2, true)
    println("Tenemos un encuentro? $match")
}