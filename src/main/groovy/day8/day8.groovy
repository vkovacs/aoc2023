package day8

inputFile = new File("../../resources/day8/input")

def directions = inputFile.readLines()[0].split("")
def nodes = [:]
inputFile.readLines().subList(2, inputFile.readLines().size()).each {
    def match = it =~ "[A-Z]{3}"
    nodes[match[0]] = [match[1], match[2]]
}

//part1

def i = 0
def steps = 0
def actualNode = "AAA"
while (true) {
    if (directions[i] == "L") actualNode = nodes[actualNode][0]
    else actualNode = nodes[actualNode][1]

    steps = ++steps

    if (actualNode == "ZZZ") break

    i = (i + 1) % directions.size()
}

println steps