package day8

import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

val file = File("/Users/catherinewang/Documents/AdventOfCode2025/src/day8/day8_input.txt")

// Day 8: Connect together the 1000 pairs of junction boxes that are
// closest together. Determine the product of the sizes of the three
// largest circuits.
fun dayEight() {
    // Get all points and distances
    val points = parseAllPoints(file)
    val measurements = calculateAllDistances(points)

    // Sort the measurements between points from smallest to largest
    measurements.sortBy { it.distance }

    val circuits = mutableListOf<MutableSet<Point>>()
    for (i in 0..<1000) {
        val currMeasurement = measurements[i]

        // Check if any of the points are part of an existing circuit
        val circuitsFound = circuits.filter {
            it.contains(currMeasurement.points.first) || it.contains(currMeasurement.points.second)
        }
        if (circuitsFound.isNotEmpty()) {
            circuitsFound[0].addAll(currMeasurement.points.toList())

            if (circuitsFound.size == 2) {
                // Need to combine overlapping circuits into one
                circuitsFound[0].addAll(circuitsFound[1])
                circuits.remove(circuitsFound[1])
            }
        } else {
            circuits.add(currMeasurement.points.toList().toMutableSet())
        }
    }

    // Sort the circuits by the most to the least items in the circuit
    circuits.sortByDescending { it.size }

    println(circuits[0].size * circuits[1].size * circuits[2].size)
}

// Part 2: Continue connecting the closest unconnected pairs of circuits until they're all connected.
// Return the product of the X coordinates for the last two junction boxes that are connected.
fun dayEightPartTwo() {
    // Get all points and distances
    val points = parseAllPoints(file)
    val measurements = calculateAllDistances(points)

    // Sort the measurements between points from smallest to largest
    measurements.sortBy { it.distance }

    val circuits = mutableListOf<MutableSet<Point>>()
    var isAllConnected = false
    var i = 0
    while (!isAllConnected && i < measurements.size) {
        val currMeasurement = measurements[i]

        // Check if any of the points are part of an existing circuit
        val circuitsFound = circuits.filter {
            it.contains(currMeasurement.points.first) || it.contains(currMeasurement.points.second)
        }
        if (circuitsFound.isNotEmpty()) {
            circuitsFound[0].addAll(currMeasurement.points.toList())

            if (circuitsFound.size == 2) {
                // Need to combine overlapping circuits into one
                circuitsFound[0].addAll(circuitsFound[1])
                circuits.remove(circuitsFound[1])
            }
        } else {
            circuits.add(currMeasurement.points.toList().toMutableSet())
        }

        // Check if we have connected all junction boxes
        if (circuits.size == 1 && circuits[0].size == 1000) {
            println("i: $i; ${currMeasurement.points}")
            println(currMeasurement.points.first.x * currMeasurement.points.second.x)
            isAllConnected = true
        } else {
            i++
        }
    }
}

data class Point(val x: Long, val y: Long, val z: Long)

data class Measurement(val points: Pair<Point,Point>, val distance: Double)

fun calculateEuclideanDistance(pointA: Point, pointB: Point): Double {
    val xDifference = (pointA.x - pointB.x).toDouble()
    val yDifference = (pointA.y - pointB.y).toDouble()
    val zDifference = (pointA.z - pointB.z).toDouble()
    return sqrt(xDifference.pow(2) + yDifference.pow(2) + zDifference.pow(2))
}

fun parseAllPoints(file: File): MutableList<Point> {
    val points = mutableListOf<Point>()

    file.forEachLine { line ->
        val coordinates = line.split(",")
        points.add(Point(coordinates[0].toLong(), coordinates[1].toLong(), coordinates[2].toLong()))
    }
    return points
}

fun calculateAllDistances(points: List<Point>): MutableList<Measurement> {
    val measurements = mutableListOf<Measurement>()

    for (i in 0..<points.size-1) {
        val currentPoint = points[i]
        for (j in i+1..<points.size) {
            val otherPoint = points[j]
            val distance = calculateEuclideanDistance(currentPoint, otherPoint)

            measurements.add(Measurement(Pair(currentPoint, otherPoint), distance))
        }
    }
    return measurements
}