package day1

def inputFile = new File("../../resources/day1/input")

//part1
def sum(List<String> lines) {
    lines.collect {
        it ->
            def firstNumber = it.findAll(/\d/)?.first()
            def lastNumber = it.findAll(/\d/)?.last()
            def combinedNumber = firstNumber + lastNumber
            combinedNumber as int
    }.sum()
}

println sum(inputFile.readLines())

//part2

def replaceNumber(String line) {
    def numbers = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"]
    def result = ""
    for (i in 0..<line.size()) {
        if (line[i].isNumber()) {
            result += line[i]
        } else {
            for (j in 0..<numbers.size()) {
                if (line.substring(i).startsWith(numbers[j])) {
                    result += (j + 1) as String
                }
            }
        }
    }
    result
}

def replacedList = inputFile.readLines().collect {
    replaceNumber(it)
}

println sum(replacedList)