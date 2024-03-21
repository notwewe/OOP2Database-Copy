package com.example.midterm.Fibonacci;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Fibonacci {
    static final List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Enter the number of terms: ");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        scanner.close();

        for (int i = 0; i < size; i++) {
            threads.add(new Thread(new FibRunnable(i + 1)));
        }

        FibRunnable.result = new int[size];
        threads.get(size - 1).start();

        try {
            threads.get(size - 1).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(FibRunnable.result));
    }
}