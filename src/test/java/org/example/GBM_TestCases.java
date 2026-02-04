package org.example;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;

public class GBM_TestCases {

    @Test
    void insertValidCoinsAccumulatesCredit() {
        GBM_Logic m = new GBM_Logic();
        m.insertCoin(5);
        m.insertCoin(10);
        m.insertCoin(25);
        assertEquals(40, m.getCreditCents());
        assertEquals(List.of(), m.getCoinReturnSnapshot());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 6, 7, 11, 30, 100})
    void invalidCoinsMoveToCoinReturnBin(int invalid) {
        GBM_Logic m = new GBM_Logic();
        m.insertCoin(invalid);
        assertEquals(0, m.getCreditCents());
        assertEquals(List.of(invalid), m.getCoinReturnSnapshot());
    }

    @Test
    void leverPressinsufficientBallanceNoDispensse() {
        GBM_Logic m = new GBM_Logic();
        assertFalse(m.pressLever(GBM_Logic.Color.YELLOW));
        assertEquals(0, m.getCreditCents());
    }

    @Test
    void dispenseOneGumballAtATime() {
        GBM_Logic m = new GBM_Logic();
        m.insertCoin(10); // ok for 2 RED or 1 YELLOW
        assertTrue(m.pressLever(GBM_Logic.Color.RED));
        assertEquals(5, m.getCreditCents());
        assertTrue(m.pressLever(GBM_Logic.Color.RED));
        assertEquals(0, m.getCreditCents());
        assertFalse(m.pressLever(GBM_Logic.Color.RED)); // => insufficient balance
    }

    @Test
    void insert25CentsDispense2RedReturn15Cents() {
        GBM_Logic m = new GBM_Logic();
        m.insertCoin(25);

        assertTrue(m.pressLever(GBM_Logic.Color.RED));  // -5 => 20
        assertTrue(m.pressLever(GBM_Logic.Color.RED));  // -5 => 15
        assertEquals(15, m.getCreditCents());

        List<Integer> returned = m.returnMyChange();
        assertEquals(List.of(15), returned);
        assertEquals(0, m.getCreditCents());
    }

    @Test
    void returnMyChangeReturnsInvalidCoinsPlusRemainingCredit() {
        GBM_Logic m = new GBM_Logic();
        m.insertCoin(50);   // invalid
        m.insertCoin(25);  // credit
        assertTrue(m.pressLever(GBM_Logic.Color.YELLOW)); // -10 => 15

        List<Integer> returned = m.returnMyChange();
        assertEquals(List.of(50, 15), returned); // one invalid coin (3) & remaining change (15)
        assertEquals(0, m.getCreditCents());
        assertEquals(List.of(), m.getCoinReturnSnapshot());
    }

    @Test
    void returnMyChangeWhenNothingInsertedReturnsOnlyInvalidsOrEmpty() {
        GBM_Logic m1 = new GBM_Logic();
        assertEquals(List.of(), m1.returnMyChange());

        GBM_Logic m2 = new GBM_Logic();
        m2.insertCoin(99);
        assertEquals(List.of(99), m2.returnMyChange());
    }
}

