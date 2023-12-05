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

def chainedMappingResults = [:]

seed = [3943078016]

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
    println(chainedMappingResults)
}

println(chainedMappingResults)

println chainedMappingResults.values().min()

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
            //if (number in sourceRangeStart..<sourceRangeStart + range) {
            def difference = number - sourceRangeStart
            return destinationRangeStart + difference
        }
        null
    }
}


