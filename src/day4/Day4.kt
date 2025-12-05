package day4

import java.io.File

// A roll of paper can only be accessed if there are fewer than 4 rolls of paper
// in the 8 adjacent positions (e.g. 3 top, 2 side, 3 bottom positions).
// Day 4: Count the total number of paper rolls that can be accessed by a forklift
// Part 2: Count the max number of paper rolls that can be accessed, if continuously removed.
fun dayFour() {
    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day4/day4_input.txt")
    val inputMatrix = convertToMatrix(file)
    var result = 0
    var prevResult = -1

    while (result != prevResult) { // Don't use this while loop for part 1
        prevResult = result
        inputMatrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, square ->
                var adjacentRolls = 0
                if (square == '@') {
                    // Check if the paper has less than 4 adjacant rolls
                    // Check the row above
                    if ((i - 1) >= 0) {
                        if ((j - 1) >= 0 && inputMatrix[i - 1][j - 1] == '@') {
                            adjacentRolls += 1
                        }
                        if (inputMatrix[i - 1][j] == '@') {
                            adjacentRolls += 1
                        }
                        if ((j + 1) < row.size && inputMatrix[i - 1][j + 1] == '@') {
                            adjacentRolls += 1
                        }
                    }

                    // Check the current row
                    if ((j - 1) >= 0 && inputMatrix[i][j - 1] == '@') {
                        adjacentRolls += 1
                    }
                    if ((j + 1) < row.size && inputMatrix[i][j + 1] == '@') {
                        adjacentRolls += 1
                    }

                    // Check the row below
                    if ((i + 1) < inputMatrix.size) {
                        if ((j - 1) >= 0 && inputMatrix[i + 1][j - 1] == '@') {
                            adjacentRolls += 1
                        }
                        if (inputMatrix[i + 1][j] == '@') {
                            adjacentRolls += 1
                        }
                        if ((j + 1) < row.size && inputMatrix[i + 1][j + 1] == '@') {
                            adjacentRolls += 1
                        }
                    }

                    if (adjacentRolls < 4) {
                        result += 1
                        inputMatrix[i][j] = 'x' // Comment this out for part 1 answer!
                    }
                }
            }
            //  println(row)
        }
        // println("$prevResult, $result")
    }
    println(result)
}

fun convertToMatrix(file: File): MutableList<MutableList<Char>> {
    val output = mutableListOf<MutableList<Char>>()

    file.forEachLine { line ->
        output.add(line.toMutableList())
    }
    return output
}

// Convert the input to be a list[list[string]] (e.g. [['.', '@', '@', '.'], ['.', '@', '@', '.']]
// Iterate through each row of paper
    // Skip if the square doesn't have any paper
// Check if the paper has less than 4 adjacent rolls
    // Check top left [i-1][j-1], top center [i-1][j], top right [i-1][j+1]
    // Check left [i][j-1] and right [i][j+1]
    // Check bottom left [i+1][j-1], [i+1][j], [i+1][j+1]
// If so, increment resulting counter.