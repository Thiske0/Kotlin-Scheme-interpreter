

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Please provide the file path of the file to count the amount of tail recursions.")
        return
    }
    val path = args[0]
    val program = FileParser.parseFile(path)
    if(program.entries.size != 1) {
        println("Program should contain exactly 1 expression.")
        return
    }
    val tailRecursionCount = TailRecursionCounter.countTailRecursion(program.entries[0])
    println(tailRecursionCount)
}