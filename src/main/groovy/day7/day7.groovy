package day7

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.util.stream.Collectors

inputFile = new File("../../resources/day7/input")

def handBids = inputFile.readLines().collect {
    line ->
        def split = line.split(" ")
        new HandBid(split[0], split[1] as int)
}

def sortedHandBids = handBids.sort()
def sum = 0
for (i in (1..sortedHandBids.size())) {
    sum += handBids[i - 1].bid * i
}
println sum

@ToString
class HandBid implements Comparable<HandBid> {
    List<Card> hand
    int bid

    HandBid(String hand, int bid) {
        this.hand = hand.split("").collect { new Card(it) }
        this.bid = bid
    }

    Type determineType() {
        def groupedBy = hand.stream()
                .collect(Collectors.groupingBy(it -> it, Collectors.counting()))

        def maxValue = groupedBy.values().max()

        if (maxValue == 5) return Type.FIVE_OF_A_KIND
        if (maxValue == 4) return Type.FOUR_OF_A_KIND
        if (groupedBy.values().count(3) == 1 && groupedBy.values().count(2) == 1) return Type.FULL_HOUSE
        if (groupedBy.values().count(3) == 1) return Type.THREE_OF_A_KIND
        if (groupedBy.values().count(2) == 2) return Type.TWO_PAIR
        if (groupedBy.values().count(2) == 1) return Type.ONE_PAIR
        Type.HIGH_CARD

    }

    @Override
    int compareTo(HandBid other) {
        if (this.determineType().ordinal() < other.determineType().ordinal()) return -1
        if (this.determineType().ordinal() > other.determineType().ordinal()) return 1

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

    enum Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }


}

@ToString
@EqualsAndHashCode
class Card implements Comparable<Card> {
    String cardString
    int value

    Card(String cardString) {
        this.cardString = cardString
        if (cardString == "A") value = 13
        if (cardString == "K") value = 12
        if (cardString == "Q") value = 11
        if (cardString == "J") value = 10
        if (cardString == "T") value = 9
        if (cardString.isNumber()) value = (cardString as int) - 1
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
