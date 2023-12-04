package day4

inputFile = new File("../../resources/day4/input")

//part1
def winningNumbersCount(def allNumbers) {
    def winnerNumbers = allNumbers[0].split(" ").findAll { it != "" }
    def myNumbers = allNumbers[1].split(" ").findAll { it != "" }
    winnerNumbers.intersect(myNumbers).size() as int
}

def cardValues = []
inputFile.readLines().each { line ->
    def colonSplit = line.split(":")
    def allNumbers = colonSplit[1].split(" \\| ")
    def winningNumberCount = winningNumbersCount(allNumbers)

    cardValues << ((winningNumberCount - 1 >= 0 ? Math.pow(2, winningNumberCount - 1) : 0) as int)


}

println cardValues.sum() as int

//part2
Map<Integer, Integer> wonCardMap = [:].withDefault { 1 }
inputFile.readLines().each { line ->
    def colonSplit = line.split(":")
    def gameId = (colonSplit[0] =~ /Card[ ]+(\d+)/)[0][1] as int
    def allNumbers = colonSplit[1].split(" \\| ")
    def winningNumbersCount = winningNumbersCount(allNumbers)
    wonCardMap[gameId] = wonCardMap[gameId] //to make uninitialized map keys set for default value (1)
    for (i in 0..<winningNumbersCount) {
        wonCardMap[gameId + (i + 1)] += wonCardMap[gameId]
    }
}

println wonCardMap.values().sum()