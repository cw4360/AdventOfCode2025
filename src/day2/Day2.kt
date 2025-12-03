package day2

import java.io.File

// Only a few product ID ranges to check
// Ranges are separated by commas, and each range is separated by a dash.
// Invalid ID means that a sequence of digits is repeated exactly twice (e.g. 123123)
// No leading zeroes.
// Day 1: Find and determine the sum of all the invalid IDs
fun dayTwo() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day2/day2_input.txt")
    val inputList = file.readText(Charsets.UTF_8).split(",")
    var result = 0L

    inputList.forEach {
        // Read the range
        val range = it.split("-")
        val start = range[0].toLong()
        val end = range[1].toLong()

        // Can also skip the range if all IDs are of odd length (i.e. always valid)
        // Scan through every num in range
        for (i in start..end) {
            val id = i.toString()
            val idLength = id.length
            val mid = idLength / 2

            // Check if the number is invalid
            // The number can be invalid if length is even
            if (idLength % 2 == 0) {
                if (id.substring(0,mid) == id.substring(mid)) {
                    result += i
                }
            }
        }
    }
    println(result)
}

// Part 2: An ID can be invalid if it is made of a sequence of digits repeated
// at least twice
fun dayTwoPartTwo() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day2/day2_input.txt")
    val inputList = file.readText(Charsets.UTF_8).split(",")
    var result = 0L

    inputList.forEach {
        // Read the range
        val range = it.split("-")
        val start = range[0].toLong()
        val end = range[1].toLong()

        // Iterate through each number in the range
        for (i in start..end) {
            val id = i.toString()
            val idLength = id.length
            val mid = idLength / 2

            // Try all split sizes from 1 to the mid-length
            for (splitSize in 1..mid) {
                // Only try split sizes that are possible
                if (idLength % splitSize == 0) {
                    // Convert the string into a char array of X chars (e.g. ["21, "21"]
                    val charArray = splitIntoCharArray(id, splitSize)

                    // If every number is the same, then add ID to result
                    val c = charArray[0]
                    var isMatching = true
                    for (char in charArray) {
                        if (char != c) {
                            isMatching = false
                            break
                        }
                    }
                    if (isMatching) {
                        result += i
                        break // Only add each distinct number once
                    }
                }
            }
        }
    }
    println(result)
}

fun splitIntoCharArray(string: String, splitSize: Int): List<String> {
    val charArray = mutableListOf<String>()
    var i = 0

    // Return an empty array if splitSize is not possible
    if (string.length % splitSize != 0) return charArray

    while (i < string.length) {
        charArray.add(string.substring(i, i + splitSize))
        i += splitSize
    }

    return charArray
}


// Brute force:
// For each range,
// For each number in the range,
// Check if the number is invalid
// (a.k.a. the first half of num == second half of num)
// Time complexity: O(n*k) where k is the length of the range
// Space complexity: O(1), keep track of resulting sum

// Another solution:
// For each range,
// We compute all the possible invalid IDs
// Then check if this ID is within the range
// If is in range, then add to sum and find the next one
// Else, stop looking for this range
