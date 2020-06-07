/*
 * Сreated by Igor Pokrovsky. 2020/5/8
 */

package com.gmail.nigtime456.weatherapplication.data.forecast

import org.junit.Assert.assertEquals
import org.junit.Test

class WindTest {

    /**
    directions:
    N   -> 348.75 - 11.25
    NNE -> 11.25  - 35.75
    NE  -> 35.75  - 56.25
    ENE -> 56.25  - 78.75
    E   -> 78.75  - 101.25
    ESE -> 101.25 - 123.75
    SE  -> 123.75 - 146.25
    SSE -> 146.25 - 168.75
    S   -> 168.75 - 191.25
    SSW -> 191.25 - 213.75
    SW  -> 213.75 - 236.25
    WSW -> 236.25 - 258.75
    W   -> 258.75 - 281.25
    WNW -> 281.25 - 303.75
    NW  -> 303.75 - 326.25
    NNW -> 326.25 - 348.75
     */
    @Test
    fun converting_degrees_to_cardinal_direction() {
        val n = Wind.CardinalDirection.fromDegrees(349)
        val n2 = Wind.CardinalDirection.fromDegrees(10)

        val nne = Wind.CardinalDirection.fromDegrees(12)
        val nne2 = Wind.CardinalDirection.fromDegrees(33)

        val ne = Wind.CardinalDirection.fromDegrees(36)
        val ne2 = Wind.CardinalDirection.fromDegrees(55)

        val ene = Wind.CardinalDirection.fromDegrees(57)
        val ene2 = Wind.CardinalDirection.fromDegrees(77)

        val e = Wind.CardinalDirection.fromDegrees(79)
        val e2 = Wind.CardinalDirection.fromDegrees(100)

        val ese = Wind.CardinalDirection.fromDegrees(102)
        val ese2 = Wind.CardinalDirection.fromDegrees(122)

        val se = Wind.CardinalDirection.fromDegrees(124)
        val se2 = Wind.CardinalDirection.fromDegrees(145)

        val sse = Wind.CardinalDirection.fromDegrees(147)
        val sse2 = Wind.CardinalDirection.fromDegrees(167)

        val s = Wind.CardinalDirection.fromDegrees(169)
        val s2 = Wind.CardinalDirection.fromDegrees(190)

        val ssw = Wind.CardinalDirection.fromDegrees(192)
        val ssw2 = Wind.CardinalDirection.fromDegrees(212)

        val sw = Wind.CardinalDirection.fromDegrees(214)
        val sw2 = Wind.CardinalDirection.fromDegrees(235)

        val wsw = Wind.CardinalDirection.fromDegrees(237)
        val wsw2 = Wind.CardinalDirection.fromDegrees(257)

        val w = Wind.CardinalDirection.fromDegrees(259)
        val w2 = Wind.CardinalDirection.fromDegrees(280)

        val wnw = Wind.CardinalDirection.fromDegrees(282)
        val wnw2 = Wind.CardinalDirection.fromDegrees(302)

        val nw = Wind.CardinalDirection.fromDegrees(304)
        val nw2 = Wind.CardinalDirection.fromDegrees(326)

        val nnw = Wind.CardinalDirection.fromDegrees(327)
        val nnw2 = Wind.CardinalDirection.fromDegrees(348)



        assertEquals(n, Wind.CardinalDirection.N)
        assertEquals(n2, Wind.CardinalDirection.N)

        assertEquals(nne, Wind.CardinalDirection.NNE)
        assertEquals(nne2, Wind.CardinalDirection.NNE)

        assertEquals(ne, Wind.CardinalDirection.NE)
        assertEquals(ne2, Wind.CardinalDirection.NE)

        assertEquals(ene, Wind.CardinalDirection.ENE)
        assertEquals(ene2, Wind.CardinalDirection.ENE)

        assertEquals(e, Wind.CardinalDirection.E)
        assertEquals(e2, Wind.CardinalDirection.E)

        assertEquals(ese, Wind.CardinalDirection.ESE)
        assertEquals(ese2, Wind.CardinalDirection.ESE)

        assertEquals(se, Wind.CardinalDirection.SE)
        assertEquals(se2, Wind.CardinalDirection.SE)

        assertEquals(sse, Wind.CardinalDirection.SSE)
        assertEquals(sse2, Wind.CardinalDirection.SSE)

        assertEquals(s, Wind.CardinalDirection.S)
        assertEquals(s2, Wind.CardinalDirection.S)

        assertEquals(ssw, Wind.CardinalDirection.SSW)
        assertEquals(ssw2, Wind.CardinalDirection.SSW)

        assertEquals(sw, Wind.CardinalDirection.SW)
        assertEquals(sw2, Wind.CardinalDirection.SW)

        assertEquals(wsw, Wind.CardinalDirection.WSW)
        assertEquals(wsw2, Wind.CardinalDirection.WSW)

        assertEquals(w, Wind.CardinalDirection.W)
        assertEquals(w2, Wind.CardinalDirection.W)

        assertEquals(wnw, Wind.CardinalDirection.WNW)
        assertEquals(wnw2, Wind.CardinalDirection.WNW)

        assertEquals(nw, Wind.CardinalDirection.NW)
        assertEquals(nw2, Wind.CardinalDirection.NW)

        assertEquals(nnw, Wind.CardinalDirection.NNW)
        assertEquals(nnw2, Wind.CardinalDirection.NNW)

    }
}