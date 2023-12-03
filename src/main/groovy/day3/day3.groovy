package day3

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

def inputFile = new File("../../resources/day3/input")

//part 1
matrix = [][]
inputFile.readLines().each {
    matrix << it.split("")
}

@ToString
@EqualsAndHashCode
class Coordinate {
    int x, y

    Coordinate(int x, int y) {
        this.x = x
        this.y = y
    }
}

directions =
        [new Coordinate(-1, -1), new Coordinate(0, -1), new Coordinate(1, -1),
         new Coordinate(-1, 0), new Coordinate(1, 0),
         new Coordinate(-1, 1), new Coordinate(0, 1), new Coordinate(1, 1)]


def partNumbers = []

for (i in 0..<matrix.size()) {
    def number = ""
    def numberCoordinates = []
    for (j in 0..<matrix[i].size()) {

        if (matrix[i][j].isNumber()) {
            number += matrix[i][j]
            numberCoordinates << new Coordinate(i, j)
        }

        if (!matrix[i][j].isNumber() || number != "" && j == matrix[i].size() - 1) {
            coordinateLoop:
            for (coordinate in numberCoordinates) {
                if (!neighbours(coordinate.x, coordinate.y).findAll {
                    it != "." && !it.isNumber()
                }.isEmpty()) {
                    partNumbers << (number as int)
                    break coordinateLoop
                }
            }
            number = ""
            numberCoordinates = []
        }
    }
}

println partNumbers.sum()

def neighbours(int i, int j) {
    Set<String> neighbours = []
    directions.each { direction ->
        neighbourX = i + direction.x
        neighbourY = j + direction.y
        if (neighbourX >= 0 && neighbourX < matrix.size() && neighbourY >= 0 && neighbourY < matrix[i].size()) {
            neighbours << matrix[neighbourX][neighbourY]
        }
    }
    neighbours
}

// part2

Map<Coordinate, List<Integer>> gears = [:].withDefault { [] }

for (i in 0..<matrix.size()) {
    def number = ""
    def numberCoordinates = []
    for (j in 0..<matrix[i].size()) {

        if (matrix[i][j].isNumber()) {
            number += matrix[i][j]
            numberCoordinates << new Coordinate(i, j)
        }

        if (!matrix[i][j].isNumber() || number != "" && j == matrix[i].size() - 1) {
            for (coordinate in numberCoordinates) {
                for (neighbour in neighboursWithCoordinates(coordinate.x, coordinate.y)) {
                    if (neighbour.key == "*") {
                        def intNumber = number as int
                        if (!gears[neighbour.value].contains(intNumber)) {
                            //a number maybe added to coordinates of different gears
                            gears[neighbour.value] << (number as int)
                        }
                    }
                }
            }
            number = ""
            numberCoordinates = []
        }
    }
}

def neighboursWithCoordinates(int i, int j) {
    Map<String, Coordinate> neighbours = [:]
    directions.each { direction ->
        neighbourX = i + direction.x
        neighbourY = j + direction.y
        if (neighbourX >= 0 && neighbourX < matrix.size() && neighbourY >= 0 && neighbourY < matrix[i].size()) {

            neighbours[matrix[neighbourX][neighbourY]] = new Coordinate(neighbourX, neighbourY)
        }
    }
    neighbours
}

println gears.findAll {
    it.value.size() == 2
}.collect {
    it.value
}.sum {
    it.inject(1) {acc, number -> acc * (number as int)}
}