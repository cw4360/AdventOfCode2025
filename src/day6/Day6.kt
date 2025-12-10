package day6

import java.io.File

// Day 6: Add or multiply all the numbers within a column. Then,
// return the sum of each addition and multiplication problem.
// Correct answer: 3261038365331!
// Time complexity: O(m*n)
// Space complexity: O(m*n)
fun daySix() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day6/day6_input.txt")
    var result = 0L

    // Parse the data
    val input = convertInputToMatrix(file)

    // For each column
    for (c in 0..<input[0].size) {
        // Get the operation ('+' or '*')
        val operation = input.last()[c]
        var answer = if (operation == "*") { 1L } else { 0L }

        // Perform the operation on every number in the column
        for (r in 0..(input.size-2)) {
            answer = if (operation == "*") {
                answer * input[r][c].toLong()
            } else {
                answer + input[r][c].toLong()
            }
        }
        // Add the final sum/product to the end result
        result += answer
    }

    println(result)
}

// Part two: Return the sum of each addition and multiplication problem; however,
// each number is represented vertically from right to left.
// Main problem now is how to parse the data.
// Correct answer: 8342588849093!
// Time complexity: O(m*n)
// Space complexity: O(m*n)
fun daySixPartTwo() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day6/day6_input.txt")
    var result = 0L

    // Parse the data
    val input = convertInputToMatrix(file = file, isPartTwo = true)

    // For each column
    var operation = ""
    var answer = 0L
    for (c in 0..<input[0].size) {
        // Read the column as a row
        var number = ""
        for (r in input.indices) {
            number += input[r][c]
        }

        number = number.replace(" ", "") // Get only the digits and operation

        // If the column is fully blank, then skip to the next column and add the answer to result
        if (number.isBlank()) {
            result += answer
            continue
        } else if (!number.last().isDigit()) {
            // If the last char of the column is '+' or '*', then we can read the operation, and start the problem
            operation = number.last().toString()
            number = number.dropLast(1)
            answer = if (operation == "+") { 0L } else { 1L }
        }

        // We accumulate the sum/product until we encounter a space or there is no more
        when (operation) {
            "+" -> { answer += number.toLong() }
            "*" -> { answer *= number.toLong() }
        }
    }
    println(result)
}

fun convertInputToMatrix(
    file: File,
    isPartTwo: Boolean = false
): List<List<String>> {
    val result = mutableListOf<List<String>>()

    file.forEachLine { line ->
        if (isPartTwo) {
            result.add(line.split(""))
        } else {
            result.add(line.split("\\s+".toRegex()))
        }
    }
    if (isPartTwo) {
        // Add an extra empty space so that all lists are the same size.
        result[result.size - 1] = result.last().plus("")
    }
    return result
}