package day5

inputFile = new File("../../resources/day5/input")

def seeds = []
def maps = [:].withDefault { [] }
def actualMap = ""
inputFile.readLines().each { line ->
    if (line.startsWith("seeds:")) {
        seeds = line.split(":")[1].trim().split(" ").collect { it -> it as long }
        println(seeds)
    }

    //noinspection GroovyFallthrough
    switch (line) {
        case "seed-to-soil map:": actualMap = "seed-to-soil"; break
        case "soil-to-fertilizer map:": actualMap = "soil-to-fertilizer"; break
        case "fertilizer-to-water map:": actualMap = "fertilizer-to-water"; break
        case "water-to-light map:": actualMap = "water-to-light"; break
        case "light-to-temperature map:": actualMap = "light-to-temperature"; break
        case "temperature-to-humidity map:": actualMap = "temperature-to-humidity"; break
        case "humidity-to-location map:": actualMap = "humidity-to-location"; break
    }

    def match = (line =~ /(\d+) (\d+) (\d+)/)
    if (match.matches()) {
        def destinationRangeStart = (match[0][1] as long)
        def sourceRangeStart = (match[0][2] as long)
        def range = (match[0][3] as long)

        def mapping = new Mapping(sourceRangeStart, destinationRangeStart, range)
        maps[actualMap] << mapping
    }
}

def part1(def seeds, def maps) {
    def chainedMappingResults = [:]

    for (seed in seeds) {
        def chainedMappedValue = seed
        for (key in maps.keySet()) {
            mappedValue = null
            for (mapping in (maps[key])) {
                def mappingResult = mapping.mappingFor(chainedMappedValue)
                if (mappingResult != null) {
                    mappedValue = mappingResult
                    break
                }
            }
            if (mappedValue == null) {
                mappedValue = chainedMappedValue
            }
            chainedMappedValue = mappedValue

        }
        chainedMappingResults.put(seed, chainedMappedValue)
        //println(chainedMappingResults)
    }
    chainedMappingResults
}


//part1
println part1(seeds, maps).values().min()

//part2
minLocation = Long.MAX_VALUE
for (int i = 0; i < seeds.size(); i = i + 2) {
    println("i: " + i)
    for (j in 0..<seeds[i + 1]) {
        long seed = seeds[i] + j
        if (seed % 10_000_000 == 0) println seed
        def location = part2(seed, maps)
        if (location < minLocation) {
            minLocation = location
        }
    }
}

println minLocation

def part2(def seed, def maps) {
    def chainedMappedValue = seed
    for (key in maps.keySet()) {
        mappedValue = null
        for (mapping in (maps[key])) {
            def mappingResult = mapping.mappingFor(chainedMappedValue)
            if (mappingResult != null) {
                mappedValue = mappingResult
                break
            }
        }
        if (mappedValue == null) {
            mappedValue = chainedMappedValue
        }
        chainedMappedValue = mappedValue

    }
    chainedMappedValue
}

class Mapping {
    long sourceRangeStart
    long destinationRangeStart
    long range

    Mapping(long sourceRangeStart, long destinationRangeStart, long range) {
        this.sourceRangeStart = sourceRangeStart
        this.destinationRangeStart = destinationRangeStart
        this.range = range
    }

    Long mappingFor(long number) {
        if (sourceRangeStart <= number && number < sourceRangeStart + range) {
            def difference = number - sourceRangeStart
            return destinationRangeStart + difference
        }
        null
    }
}


