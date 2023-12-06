package day6

inputFile = new File("../../resources/day6/input")

def lines = inputFile.readLines()

def times = lines[0].split(":")[1].replaceAll(/ +/, " ").trim().split(" ").collect { it as int }
def distances = lines[1].split(":")[1].replaceAll(/ +/, " ").trim().split(" ").collect { it as int }

def successCounts = []

for (i in 0..<times.size()) {
    def halfHoldTime = (times[i] / 2) as double
    def halfHoldTimeCeil = Math.ceil(halfHoldTime) as int
    def range = 0
    if (travelledDistance(times[i], halfHoldTimeCeil) <= distances[i]) return

    while (distances[i] < travelledDistance(times[i], halfHoldTimeCeil + range)) {
        range++
    }

    def successCount = range * 2
    if (halfHoldTime % 1 == 0) {
        successCount = --successCount //middle element does not count twice
    }

    successCounts << successCount
}

println successCounts
println successCounts.inject(1) {acc, it -> acc * it}

int travelledDistance(int maxTime, int holdTime) {
    (maxTime - holdTime) * holdTime
}
