fun main(args: Array<String>){
    val num1 = 10
    val num2 = 15

    val match = (num1==num2)
    println("Match= $match")

    //val match2 = num1.equals(num2)
    //println("Match= $match2")

    println("Comparando resultados = ${num1.compareTo(num2)}")
}