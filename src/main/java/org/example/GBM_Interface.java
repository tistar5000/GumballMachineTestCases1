package org.example;

import java.util.List;
import java.util.Scanner;

public class GBM_Interface {

    public static void main(String[] args) {
        GBM_Logic machine = new GBM_Logic();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        System.out.println("| Gumball Machine Simulator |");
        System.out.println(" _ _ _ _ _ _ _ _ _ _ _ _ _ _");

        while (true) {
            System.out.println("\nAvailable credit: " + machine.getCreditCents() + "¢\n");
            System.out.println("Enter number to perform corresponding task:");
            System.out.println("[ 1 ] Insert coin (accepted values: 5¢, 10¢, & 25¢)");
            System.out.println("[ 2 ] Dispense RED gumball (5¢)");
            System.out.println("[ 3 ] Dispense YELLOW gumball (10¢)");
            System.out.println("[ 4 ] Return remaining coins");
            System.out.println("[ 5 ] Display invalid coin returns bin");
            System.out.println("[ 0 ] Exit\n");
            System.out.println("Task #: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter coin value in ¢ to deposit: ");
                    String raw = sc.nextLine().trim();
                    try {
                        int coin = Integer.parseInt(raw);
                        machine.insertCoin(coin);
                        System.out.println("Inserted " + coin + "¢");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid entry. Input ignored.");
                    }
                }
                case "2" -> {
                    boolean ok = machine.pressLever(GBM_Logic.Color.RED);
                    System.out.println(ok ? "Dispensed RED gumball" : "Insufficient credit for RED.");
                }
                case "3" -> {
                    boolean ok = machine.pressLever(GBM_Logic.Color.YELLOW);
                    System.out.println(ok ? "Dispensed YELLOW gumball" : "Insufficient credit for YELLOW.");
                }
                case "4" -> {
                    List<Integer> returned = machine.returnMyChange();
                    System.out.println("Returned: " + returned);
                }
                case "5" -> System.out.println("Coin returns bin: " + machine.getCoinReturnSnapshot());
                case "0" -> {
                    System.out.println("Simulation shutdown");
                    return;
                }
                default -> System.out.println("Unknown input");
            }
        }
    }
}
