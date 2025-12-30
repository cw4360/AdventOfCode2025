package day7

import java.io.File

// Read the file and convert input into a matrix
val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day7/day7_input.txt")
val input = convertInputToMatrix(file)

// Day 7: Count the total number of times that the tachyon beam is split
// The tachyon beam starts at S, always falls downward, and is split into two, if the
// beam falls on a splitter: ^.
// Part two: Count the total possible paths a tachyon beam may have traveled.
fun daySeven() {
    var result = 0L
    val beamPositions = ArrayDeque<Pair<Int, Int>>() // Used as a queue

    // Locate the position of the beam(s), starting with S (use a list)
    for (i in 0..<input[0].size) {
        if (input[0][i] == "S") {
            beamPositions.addLast(Pair(0, i))
            break
        }
    }

    // For each row
    while (beamPositions.isNotEmpty() && ((beamPositions.first().first + 1) < input.size)) {
        val beamPos = beamPositions.removeFirst()
        val nextPos = Pair(beamPos.first+1, beamPos.second)

        // See if the beam lands on a splitter
        val nextRow = input[nextPos.first]
        when (nextRow[nextPos.second]) {
            "." -> {
                nextRow[nextPos.second] = "|"
                beamPositions.addLast(nextPos)
            }
            // If so, we should split the beam, keep track of where the beam is,
            // replace the dot with a line, and increment the resulting count.
            "^" -> {
                if (nextRow[nextPos.second-1] == ".") {
                    nextRow[nextPos.second-1] = "|"
                    beamPositions.addLast(Pair(nextPos.first, nextPos.second-1))
                }
                if (nextRow[nextPos.second+1] == ".") {
                    nextRow[nextPos.second+1] = "|"
                    beamPositions.addLast(Pair(nextPos.first, nextPos.second+1))
                }
                result += 1
            }
        }
    }
    println(result)
}

// Part two: Count the total possible paths a tachyon beam may have traveled.
// Based on Jonathan Paulson's dynamic programming solution posted on YouTube.
val memo = mutableMapOf<Pair<Int,Int>, Long>()

fun daySevenPartTwoGuided() {
    val startingBeam = locateBeamStartingPosition(input[0])

    val result = if (startingBeam != Pair(-1,-1)) {
        score(startingBeam)
    } else 0L

    println(result)
}

fun score(coordinate: Pair<Int,Int>): Long {
    val row = coordinate.first
    val col = coordinate.second
    if (row + 1 == input.size) return 1L
    if (memo.containsKey(coordinate)) {
        return memo[coordinate]!!
    }

    val result = if (input[row][col] == "^") {
        score(Pair(row + 1, col - 1)) + score(Pair(row + 1, col + 1))
    } else {
        score(Pair(row + 1, col))
    }

    memo[coordinate] = result
    return result
}

fun convertInputToMatrix(file: File): MutableList<MutableList<String>> {
    val result = mutableListOf<MutableList<String>>()

    file.forEachLine { line ->
        result.add(line.split("").toMutableList())
    }
    return result
}

fun locateBeamStartingPosition(row: List<String>): Pair<Int, Int> {
    for (i in row.indices) {
        if (row[i] == "S") {
            return Pair(0,i)
        }
    }
    return Pair(-1,-1)
}

// This theoretically works, but is very inefficient because this would
// take exponential time due to the exponentially growing number of splitting paths.
// Part two: Count the total possible paths a tachyon beam may have traveled.
// Should use a stack this time, instead of a queue data structure
// We only count the path once it has reached the end
//fun daySevenPartTwo(): Long {
//    val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day7/day7_input.txt")
//    var result = 0L
//    val beamPositions = ArrayDeque<Pair<Int,Int>>() // Used as a stack
//
//    // Convert input into a matrix, and locate beam's starting position
//    val input = convertInputToMatrix(file)
//    val startingBeam = locateBeamStartingPosition(input[0])
//    if (startingBeam == Pair(-1,-1)) { return 0L } else {
//        beamPositions.addFirst(startingBeam)
//    }
//
//    // For each possible position
//    while (beamPositions.isNotEmpty()) {
//        println(beamPositions)
//        val beamPos = beamPositions.removeFirst()
//
//        if ((beamPos.first + 1) >= input.size) {
//            result += 1
//            println(result)
//            continue
//        }
//
//        // See where the beam lands, and add these possible positions to the stack
//        val nextPos = Pair(beamPos.first+1, beamPos.second)
//        val nextRow = input[nextPos.first]
//        when (nextRow[nextPos.second]) {
//            "." -> {
//                beamPositions.addFirst(nextPos)
//            }
//            "^" -> {
//                if (nextRow[nextPos.second-1] == ".") {
//                    beamPositions.addFirst(Pair(nextPos.first, nextPos.second-1))
//                }
//                if (nextRow[nextPos.second+1] == ".") {
//                    beamPositions.addFirst(Pair(nextPos.first, nextPos.second+1))
//                }
//            }
//        }
//    }
//    println(result)
//    return result
//}
