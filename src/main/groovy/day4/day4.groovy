package day4

inputFile = new File("../../resources/day4/input")

//part1
def cardValues = []
inputFile.readLines().each { line ->
    def colonSplit = line.split(":")
    def gameId = (colonSplit[0] =~ /Card[ ]+(\d+)/)[0][1] as int
    def allNumbers = colonSplit[1].split(" \\| ")
    def winnerNumbers = allNumbers[0].split(" ").findAll { it != "" }
    def myNumbers = allNumbers[1].split(" ").findAll { it != "" }
    def winningNumberCount = winnerNumbers.intersect(myNumbers).size() as int
    cardValues << (winningNumberCount - 1 >= 0 ? Math.pow(2, winningNumberCount - 1) : 0)
}

println cardValues
println cardValues.sum() as int