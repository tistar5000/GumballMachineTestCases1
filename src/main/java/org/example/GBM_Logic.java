package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Current Gumball inventory is unlimited;
 * Machine has unlimited change. */
public class GBM_Logic {

    public enum Color { RED, YELLOW }

    public static final int NICKEL = 5;
    public static final int DIME = 10;
    public static final int QUARTER = 25;

    private int creditCents = 0;

    /** Currently available credits */
    public int getCreditCents() { return creditCents; }

    /** Return queue to handle unapproved types of coins */
    private final List<Integer> coinReturn = new ArrayList<>();

    /** Display coins in return bin */
    public List<Integer> getCoinReturnSnapshot() { return List.copyOf(coinReturn); }

    /** Price data: red = 5, yellow = 10 */
    public int price(Color color) { return (color == Color.RED) ? 5 : 10; }

    /** Coin handling:
     * if approved type of coin, add to money pool;
     * if not, move into returns queue */
    public void insertCoin(int cents) {
        if (cents == NICKEL || cents == DIME || cents == QUARTER) {
            creditCents += cents;
        } else {
            coinReturn.add(cents);
        }
    }

    /** Gumball dispenser:
     * if sufficient funds, dispense 1 gumball, return 'success';
     * if not, return 'fail' */
    public boolean pressLever(Color color) {
        int cost = price(color);
        if (creditCents >= cost) {
            creditCents -= cost;
            return true;
        }
        return false;
    }

    /** Returns all remaining credit as change plus any invalid coins previously rejected. */
    public List<Integer> returnMyChange() {
        if (creditCents > 0) {
            coinReturn.add(creditCents);
            creditCents = 0;
        }
        List<Integer> out = List.copyOf(coinReturn);
        coinReturn.clear();
        return out;
    }
}

