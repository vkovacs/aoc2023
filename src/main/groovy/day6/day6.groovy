package day6

inputFile = new File("../../resources/day6/input")

def lines = inputFile.readLines()

def times = lines[0].split(":")[1].replaceAll(/ +/, " ").trim().split(" ").collect { it as int }
def distances = lines[1].split(":")[1].replaceAll(/ +/, " ").trim().split(" ").collect { it as int }

def successCounts = []

int successCount(long time, long distance) {
    def halfHoldTime = (time / 2) as double
    def halfHoldTimeCeil = Math.ceil(halfHoldTime) as long
    def range = 0

    while (distance < travelledDistance(time, halfHoldTimeCeil + range)) {
        range++
    }

    def successCount = range * 2
    if (halfHoldTime % 1 == 0) {
        successCount = --successCount //middle element does not count twice
    }

    successCount
}

//part1
for (i in 0..<times.size()) {
    successCounts << successCount(times[i], distances[i])
}
println successCounts
println successCounts.inject(1) {acc, it -> acc * it}

//part2
def longRaceTime = lines[0].split(":")[1].replaceAll(/ +/, "").trim() as long
def longRaceDistance = lines[1].split(":")[1].replaceAll(/ +/, "").trim() as long
println successCount(longRaceTime, longRaceDistance)

def travelledDistance(def maxTime, def holdTime) {
    (maxTime - holdTime) * holdTime
}
