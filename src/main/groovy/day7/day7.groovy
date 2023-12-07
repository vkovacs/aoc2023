package day7

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.util.stream.Collectors

inputFile = new File("../../resources/day7/input")

@ToString
class HandBid implements Comparable<HandBid> {
    List<Card> hand
    int bid
    def typeDeterminator

    HandBid(String hand, int bid, def typeDeterminator, def cardValueMapper) {
        this.hand = hand.split("").collect { new Card(it, cardValueMapper) }
        this.bid = bid
        this.typeDeterminator = typeDeterminator
    }

    @Override
    int compareTo(HandBid other) {
        typeDeterminator(hand)
        if (typeDeterminator(hand).ordinal() < typeDeterminator(other.hand).ordinal()) return -1
        if (typeDeterminator(hand).ordinal() > typeDeterminator(other.hand).ordinal()) return 1

        for (i in 0..<5) {
            if (hand[i].value < other.hand[i].value) return -1
            if (hand[i].value > other.hand[i].value) return 1
        }
        0
    }

    @Override
    String toString() {
        return hand.join()
    }
}

@ToString
@EqualsAndHashCode
class Card implements Comparable<Card> {
    String cardString
    int value

    Card(String cardString, def cardValueMapper) {
        this.cardString = cardString
        this.value = cardValueMapper(cardString)
    }

    @Override
    int compareTo(Card other) {
        return this <=> other
    }

    @Override
    String toString() {
        cardString
    }
}

def baseType(def groupedBy) {
    def maxValue = groupedBy.values().max()

    if (maxValue == 5) return Type.FIVE_OF_A_KIND
    if (maxValue == 4) return Type.FOUR_OF_A_KIND
    if (groupedBy.values().count(3) == 1 && groupedBy.values().count(2) == 1) return Type.FULL_HOUSE
    if (groupedBy.values().count(3) == 1) return Type.THREE_OF_A_KIND
    if (groupedBy.values().count(2) == 2) return Type.TWO_PAIR
    if (groupedBy.values().count(2) == 1) return Type.ONE_PAIR
    return Type.HIGH_CARD
}

//part1
def typeDeterminator1 = hand -> {
    def groupedBy = hand.stream()
            .collect(Collectors.groupingBy(it -> it, Collectors.counting()))

    baseType(groupedBy)
}

def handBids1 = inputFile.readLines().collect {
    line ->
        def split = line.split(" ")
        new HandBid(split[0], split[1] as int, typeDeterminator1, (cardString) -> {
            "23456789TJQKA".findIndexOf { it == cardString }
        })
}

def part1(originalHandBids) {
    originalHandBids.collect()
            .sort()
            .withIndex().collect { handBid, int i ->
        handBid.bid * (i + 1)
    }.sum()
}

def part1Sum = part1(handBids1)
assert part1Sum == 252052080
println part1Sum

//part2
def typeDeterminator2 = hand -> {
    def jokerCount = hand.collect { it.cardString }.count { it == "J" }
    def jokerLessHand = hand.findAll { it -> it.cardString != "J" }

    def groupedBy = jokerLessHand.stream()
            .collect(Collectors.groupingBy(it -> it, Collectors.counting()))

    def type = baseType(groupedBy)

    jokerCount.times {
        type = upgrade(type)
    }
    type
}

Type upgrade(Type type) {
    switch (type) {
        case Type.HIGH_CARD: return Type.ONE_PAIR
        case Type.ONE_PAIR: return Type.THREE_OF_A_KIND
        case Type.TWO_PAIR: return Type.FULL_HOUSE
        case Type.THREE_OF_A_KIND: return Type.FOUR_OF_A_KIND
        case Type.FULL_HOUSE: return Type.FOUR_OF_A_KIND
        case Type.FOUR_OF_A_KIND: return Type.FIVE_OF_A_KIND
        case Type.FIVE_OF_A_KIND: return Type.FIVE_OF_A_KIND
    }
}

def handBids2 = inputFile.readLines().collect {
    line ->
        def split = line.split(" ")
        new HandBid(split[0], split[1] as int, typeDeterminator2, (cardString) -> {
            "J23456789TQKA".findIndexOf { it == cardString }
        })
}

def part2(originalHandBids) {
    originalHandBids.collect().sort()
            .withIndex().collect { handBid, int i ->
        handBid.bid * (i + 1)
    }.sum()
}

def sum2 = part2(handBids2)
assert sum2 == 252898370

println sum2

enum Type {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}