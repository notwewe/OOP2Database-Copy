package com.example.midterm.Fibonacci;

public class FibRunnable implements Runnable {
    private final int n;
    public static int[] result;

    public FibRunnable(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        if (n == 1) {
            result[0] = 0;
            return;
        }
        if (n == 2) {
            result[1] = 1;
            return;
        }

        try {
            if (!Fibonacci.threads.get(n - 2).isAlive()) {
                Fibonacci.threads.get(n - 2).start();
            }
            if (!Fibonacci.threads.get(n - 3).isAlive()) {
                Fibonacci.threads.get(n - 3).start();
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

        try {
            Fibonacci.threads.get(n - 2).join();
            Fibonacci.threads.get(n - 3).join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        result[n - 1] = result[n - 2] + result[n - 3];
        System.out.println(result[n - 1]);
    }
}