package se.horv.day01

import se.horv.util.Solver
import kotlin.math.abs

class Day01:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        var dial = 50
        var prevDial = 50
        var counter = 0
        for (l in lines) {
            val change = if (l.first() == 'R') { 1 } else { -1 }
            val n = l.drop(1).toInt()
            prevDial = dial
            dial = ((dial+100) + (change * n)) % 100
            if(!partTwo && dial == 0) {
                counter++
            } else {
                val wholeLaps = n/100
                val newDial = prevDial + change*n
                counter += wholeLaps
                if(prevDial != 0 && prevDial != 100 && (newDial <= 0 || newDial >= 100)) {
                    counter++
                }


            }
        }

        return counter.toString()
    }
}