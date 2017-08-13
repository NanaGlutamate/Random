class Random {
    @Volatile var now = 0
    var temp = 0
    fun add(){
        temp = now
        temp++
        now = temp
    }
    fun getRandom(): Int{
        for(i in 1..25){
            thread (start = true){
                for(j in 1..10000){
                    add()
                }
            }
        }
        while(Thread.activeCount() > 2)
            Thread.yield()
        return Math.floorMod(now,100)
    }
}
fun getRandomFloat(): Float{
    var temp = 0f
    for(i in 1..4){
        temp += (Math.pow(0.1, 2 * i)) * Random.getRandom()
    }
    return temp
}
