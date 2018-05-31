fun main(args: Array<String>) {
    val t = Classify()
    val k = Classify()
    val a = Random()
    var w = Array<Int>(100, {0})
    for(i in 1..10000){
        val (b, c) = a.getRandom()
        t.classify(b)
        k.classify(c)
        w[(10 * b) + c]++
    }
    println("tens: " + t.toString())
    println("single: " + k.toString())
    w.forEachIndexed{ i, number -> print("$i:$number ")}
}

class Random {
    @Volatile var now = 0
    var temp = 0
    fun add(){
        temp = now
        temp++
        now = temp
    }
    fun getRandom(): Pair<Int, Int>{
        for(i in 1..25){
            thread (start = true){
                for(j in 1..10000){
                    add()
                }
            }
        }
        while(Thread.activeCount() > 2)
            Thread.yield()
        var t = Math.floorMod(now,100)
        val k = t / 10
        t = Math.floorMod(t, 10)
        return Pair(k, t)
    }
}
class Classify{
    var a = Array<Int>(10, {0})
    var goTo = Array<Array<Int>>(10, {Array(10, {0})})
    var earlier: Int? = null
    fun classify(number: Int){
        a[number]++
        if(earlier != null)
            goTo[earlier!!][number]++
        earlier = number
    }
    override fun toString(): String {
        var temp: String = ""
        a.forEach { temp += it.toString() + " " }
        temp += "\n\n"
        goTo.forEachIndexed {
            i, array ->
            temp += "$i -> "
            array.forEachIndexed{ j, number ->
                temp += "$j,$number; "
            }
            temp += "\n"
        }
        return temp
    }
}
