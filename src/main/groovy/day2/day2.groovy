package day2

def inputFile = new File("../../resources/day2/input")

def maxColors = [red: 12, green: 13, blue: 14]

int sumValidGameIds = 0

for (line in inputFile.readLines()) {
    isValidLine = true
    def colonSplit = line.split(":")
    def gameId = (colonSplit[0] =~ /Game (\d+)/)[0][1] as int
    def setsString = colonSplit[1].replaceAll(" ","")
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
