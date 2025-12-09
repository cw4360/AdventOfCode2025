package day5

import java.io.File
import kotlin.math.max

// Day 5: Determine the number of available ingredients that are fresh
fun dayFive() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day5/day5_input.txt")
    var result = 0

    // Get all ranges and ingredients
    val inputs = getRangesAndIngredients(file)
    val rangesList = inputs.first
    val ingredientsSet = inputs.second

    // Iterate through the list of available ingredients
    for (ingredient in ingredientsSet) {
        // Check if they are fresh, and if so, increment the count
        for (range in rangesList) {
            val start = range[0]
            val end = range[1]

            // How do we know if they are fresh?
            // They are fresh if they are part of the ranges
            if (ingredient in start..end) {
                result += 1
                break
            }

            // TODO: We can make this more efficient by sorting the ranges from smallest to largest
            // If there are no more ranges, or the number is smaller than the start of the next range,
            // then we can end the for loop early.
        }
    }

    println(result)
}

// Part two: Count the number of ingredients that are considered fresh
fun dayFivePartTwo() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day5/day5_input.txt")
    var result = 0L

    // Get the ranges of fresh ingredients
    val inputs = getRangesAndIngredients(file)
    val rangesList = inputs.first

    // Order the ranges from smallest to largest
    val sortedList = rangesList.sortedWith(compareBy<List<Long>> { it[0] }.thenBy { it[1] })
//    val sortedList = rangesList.sortedBy { it[0] } // Algorithm still works even without sorting by the second

    // Keep track of the last processed fresh ingredient ID
    var lastProcessedID = 0L

    // For each range
    for (range in sortedList) {
        // Count the size of the range, but excluding any ID we have seen already
        val start = range[0]
        val end = range[1]

        if (lastProcessedID < start) {
            result += (end - start + 1) // Add everything because the range doesn't overlap
        } else if (lastProcessedID < end) {
            result += (end - lastProcessedID) // Add everything between the last seen and new highest
        }

        // Take the max because we always want the highest seen ID
        // The end value can sometimes be smaller because we sorted by the start value (e.g. [100, 200], [180, 199])
        lastProcessedID = max(end, lastProcessedID)
    }

    println(result)
}

fun getRangesAndIngredients(file: File): Pair<List<List<Long>>, Set<Long>> {
    val rangesList = mutableListOf<List<Long>>()
    val ingredientsSet = mutableSetOf<Long>()

    var isReadingRanges = true

    file.forEachLine { line ->
        if (line.isBlank()) {
            isReadingRanges = false
        } else {
            if (isReadingRanges) {
                val range = line.split("-")
                val start = range[0].toLong()
                val end = range[1].toLong()
                rangesList.add(listOf(start, end))

            } else {
                ingredientsSet.add(line.toLong())
            }
        }
    }

    return Pair(rangesList, ingredientsSet)
}

// Order all the ranges from smallest to largest
// Clean-up any overlapping ranges too
// Then iterate through all the ranges to see if the ingredient is part of any.