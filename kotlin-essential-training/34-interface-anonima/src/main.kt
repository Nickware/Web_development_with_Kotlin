fun main(){
val stateful = StatefulClass(object : clickListener{
    override fun onClick(event: clickEvent) {
        println("Click at ${event.x},${event.y}")
    }
})
    println("Listener iniciado")
    stateful.clickMe(5,18)
    stateful.clickMe(35,57)

}
class clickEvent(val x: Int, val y: Int)

interface clickListener{
    fun onClick(event: clickEvent)
}

class StatefulClass(listener: clickListener){
    private var clickListener:clickListener? = listener

    fun clickMe(x: Int, y: Int){
        clickListener?.onClick(clickEvent(x,y))
    }
}



