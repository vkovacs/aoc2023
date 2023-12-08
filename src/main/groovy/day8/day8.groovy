package day8

inputFile = new File("../../resources/day8/input")

def directions = inputFile.readLines()[0].split("")
def nodes = [:]
inputFile.readLines().subList(2, inputFile.readLines().size()).each {
    def match = it =~ "[A-Z0-9]{3}"
    nodes[match[0]] = [match[1], match[2]]
}

//part1
def part1(nodes, directions, startNode, endPredicate) {
    def i = 0
    long steps = 0
    def actualNode = startNode
    while (true) {
        if (directions[i] == "L") actualNode = nodes[actualNode][0]
        else actualNode = nodes[actualNode][1]

        steps = ++steps
        if (endPredicate(actualNode)) return steps

        i = (i + 1) % directions.size()
    }
}

def part1Result = part1(nodes, directions, "AAA", it -> it == "ZZZ")
println part1Result

//part2
def actualNodes = nodes.keySet().findAll { it.endsWith("A") }
def results = actualNodes.collect { part1(nodes, directions, it, n -> n.endsWith("Z")) }
println findLCM(results)

def gcd(a, b) {
    while (b) {
        def temp = b
        b = a % b
        a = temp
    }
    return a
}

def lcm(a, b) {
    return (a * b) / gcd(a, b)
}

def findLCM(numbers) {
    def result = 1
    numbers.each { number ->
        result = lcm(result, number)
    }
    return result
}