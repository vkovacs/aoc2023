package day2

def inputFile = new File("../../resources/day2/input")

//part1
def maxColors = [red: 12, green: 13, blue: 14]

int sumValidGameIds = 0

inputFile.readLines().each { line ->
    isValidLine = true
    def colonSplit = line.split(":")
    def gameId = (colonSplit[0] =~ /Game (\d+)/)[0][1] as int
    def setsString = colonSplit[1].replaceAll(" ", "")
    def setStrings = setsString.split(";")
    outer:
    for (setString in setStrings) {
        def drawStrings = setString.split(",")
        for (drawString in drawStrings) {
            def match = (drawString =~ /(\d+)(red|green|blue)/)
            int count = match[0][1] as int
            String color = match[0][2]

            if (count > maxColors[color]) {
                isValidLine = false
                break outer
            }
        }
    }

    if (isValidLine) {
        sumValidGameIds += gameId
    }
}

println sumValidGameIds

//part2
def powers = []

inputFile.readLines().each { line ->
    def setStrings = line.split(":")[1].replaceAll(" ", "").split(";")
    def (maxRed, maxBlue, maxGreen) = [0, 0, 0]

    setStrings.each { setString ->
        setString.split(",").each { draw ->
            def match = (draw =~ /(\d+)(red|green|blue)/)
            def count = match[0][1] as int
            def color = match[0][2]

            if (color == "red" && count > maxRed) {
                maxRed = count
            } else if (color == "green" && count > maxGreen) {
                maxGreen = count
            } else if (color == "blue" && count > maxBlue) {
                maxBlue = count
            }
        }
    }
    def power = maxRed * maxBlue * maxGreen

    powers << power
}
println(powers.sum())
