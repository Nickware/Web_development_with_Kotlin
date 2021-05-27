fun main(){
    val x = Any()
    println(x)

    val sup = SuperClase(42)
    println(sup)

    val sub = SubClase(53)
    println(sub)

    println(sup.multiple(100))
    println(sub.multiple(100))

}

open class SuperClase(anInt: Int){
    protected val _anInt = anInt

    override fun toString():String {
        return "${this::class.simpleName}{antInt: $_anInt}"
    }

    open fun multiple(factor: Int): Int{
        return _anInt * factor
    }
}

class SubClase(anInt: Int): SuperClase(anInt){
    override fun multiple(factor: Int): Int{
        return super.multiple(factor) * factor
    }
}