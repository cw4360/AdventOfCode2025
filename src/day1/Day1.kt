package day1

import java.io.File
import kotlin.math.floor

// (n - distance + 100) % 100: the true number

// One potential solution:
// Take the number
// Add / subtract the specified rotation distance (e.g. 1)
// If the value is less than 0, or greater than 100,
// then we need to get the true value
// If the true value is equal to 0, then we increment our resulting counter.

// Another potential solution:
// Take the number
// Add / subtract the specified rotation distance (e.g. 1)
// If the number is divisible by 0 or 100, then increment the counter.
// Aka. if the number % 100 == 0, then increment the counter.

// Day 1: Secret entrance
// There is a dial from 0 to 99.
// The dial starts by pointing at 50.
// Return the number of times that the dial points at 0 after any rotations.
// Correct answer: 1102!
fun dayOne() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day1/day1_input.txt")
    var result = 0
    var currentNum = 50

    file.useLines { lines ->
        lines.forEach { line ->
            // Determine if the rotation is L or R
            val rotationDirection = line[0]
            // Determine the rotation distance
            val rotationDistance = line.drop(1).toInt()

            if (rotationDirection == 'L') {
                currentNum -= rotationDistance
            } else {
                currentNum += rotationDistance
            }

            // Count if the resulting number is 0
            if (currentNum % 100 == 0) {
                result += 1
            }
        }
    }
    println(result)
}

// Correct answer: 6175!
// Count when the dial both passes and lands on 0.
fun dayOnePart2Guided() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day1_input.txt")
    var result = 0
    var currentNum = 50

    file.useLines { lines ->
        lines.forEach { line ->
            // Determine if the rotation direction and distance
            val rotationDirection = line[0]
            val rotationDistance = line.drop(1).toInt()

            // For each dial click, see if the dial points to zero!
            for (i in 1..rotationDistance) {
                currentNum = if (rotationDirection == 'L') {
                    (currentNum - 1 + 100) % 100
                } else {
                    (currentNum + 1) % 100
                }
                // Count if the number is 0
                if (currentNum == 0) {
                    result += 1
                }
            }
        }
    }
    println(result)
}

// Day 1, part 2:
fun dayOnePart2() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day1_input.txt")
    var result = 0
    var currentNum = 50

    file.useLines { lines ->
        lines.forEach { line ->
            val originalNum = currentNum

            // Determine the rotation direction and distance
            val rotationDirection = line[0]
            val rotationDistance = line.drop(1).toInt()

            // Count any full rotations (reduce the rotation distance)
            val numFullRotations = floor(rotationDistance / 100.0).toInt()
            result += numFullRotations

            // Get the smaller, true rotation distance
            val sRotationDistance = rotationDistance % 100

            when (rotationDirection) {
                'L' -> {
                    val newNum = currentNum - sRotationDistance

                    // If the current number wasn't 0, and the dial has passed 0,
                    // then increment the count
                    if (currentNum != 0 && newNum < 0) {
                        result += 1
                    }
                    // Note: We need to add 100 to convert this back to a positive number
                    currentNum = ((newNum + 100) % 100)
                }
                'R' -> {
                    val newNum = currentNum + sRotationDistance

                    // If the current number wasn't 0, and the dial has passed 100,
                    // then increment the count
                    if (currentNum != 0 && newNum > 100) {
                        result += 1
                    }
                    // The currentNum is always reset to the true number between 0-99.
                    currentNum = (newNum % 100)
                }
            }

            // If the dial did not start on 0, and has now landed on 0,
            // then increment the count
            if (originalNum != 0 && currentNum == 0) {
                result += 1
            }
        }
    }
    println(result)
}