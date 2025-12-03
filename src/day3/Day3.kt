package day3

import java.io.File
import kotlin.math.max

// Day 3: Batteries
// Each battery in the bank has a joltage (value from 1-9)
// Within a bank of batteries, need to turn on exactly 2.
// The bank's joltage is the number formed by the 2 batteries turned on.
// Find the maximum joltage for each bank, and return all bank's total output joltage.
// Time complexity: O(n)
// Space complexity: O(1)
fun dayThree() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day3/day3_input.txt")
    var result = 0L

    file.useLines { lines ->
        lines.forEach { bank ->
            // For each battery bank (row of batteries), find the 2 largest digits.
            var firstDigit = 0
            var secondDigit = 0

            for ((index, char) in bank.withIndex()) {
                val joltage = char.digitToInt()
                val lastIndex = bank.length - 1

                // Find the largest first and second digits
                // If the first digit is changed, then second digit must be reset too.
                if (joltage > firstDigit && index != lastIndex) {
                    firstDigit = joltage
                    secondDigit = 0
                } else if (joltage > secondDigit) {
                    secondDigit = joltage
                }
            }

            // Add the bank's joltage to result
            val bankJoltage = (firstDigit * 10) + secondDigit
            result += bankJoltage
        }
    }
    println(result)
}

// Day 3, part two: Find the total output joltage when turning on 12 batteries in a bank.
fun dayThreePartTwo(batteryCount: Int) {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day3/day3_input.txt")
    var result = 0L

    file.useLines { lines ->
        lines.forEach { bank ->
            // For each battery bank (row of batteries), find the largest joltage with 12 digits.
            val intArray = IntArray(batteryCount) // intArray of all zeroes

            for ((index, char) in bank.withIndex()) {
                val joltage = char.digitToInt()

                // Check which starting battery digit can be replaced
                val firstReplaceableIndex = max(0,batteryCount - (bank.length - index))

                // See if any battery digits can be swapped for larger values
                for (i in firstReplaceableIndex..<batteryCount) {
                    if (joltage > intArray[i]) {
                        intArray[i] = joltage

                        // Replace the remaining values with zero
                        for (j in (i+1)..<batteryCount) {
                            intArray[j] = 0
                        }
                        break
                    }
                }
            }

            // Add the bank's joltage to result
            var bankJoltage = ""
            for (num in intArray) {
                bankJoltage += num.toString()
            }
            result += bankJoltage.toLong() // Use a long value, because int is too small
        }
    }
    println(result)
}