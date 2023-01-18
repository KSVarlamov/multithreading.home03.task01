package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger counter3 = new AtomicInteger(0);
    private static final AtomicInteger counter4 = new AtomicInteger(0);
    private static final AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread palindrome = new Thread(() -> {
            for (String s : texts) {
                StringBuffer sb = new StringBuffer(s);
                boolean isPalindrome = sb.reverse().toString().equals(s);
                if (isPalindrome) {
                    switch (s.length()) {
                        case 3:
                            counter3.incrementAndGet();
                            break;
                        case 4:
                            counter4.incrementAndGet();
                            break;
                        case 5:
                            counter5.incrementAndGet();
                    }
                }
            }
        });
        Thread monoLetter = new Thread(() -> {
            for (String s : texts) {
                boolean isMonoLetter = true;
                int length = s.length();
                char first = s.charAt(0);
                for (int i = 1; i < (length); i++) {
                    if (s.charAt(i) != first) {
                        isMonoLetter = false;
                        break;
                    }
                }
                if (isMonoLetter) {
                    switch (length) {
                        case 3:
                            counter3.incrementAndGet();
                            break;
                        case 4:
                            counter4.incrementAndGet();
                            break;
                        case 5:
                            counter5.incrementAndGet();
                    }
                }
            }
        });
        Thread sortedStr = new Thread(() -> {
            for (String s : texts) {
                boolean isSorted = true;
                int length = s.length();
                char current = s.charAt(0);
                for (int i = 1; i < (length); i++) {
                    if (s.charAt(i) < current) {
                        isSorted = false;
                        break;
                    } else {
                        current = s.charAt(i);
                    }
                }
                if (isSorted) {
                    switch (length) {
                        case 3:
                            counter3.incrementAndGet();
                            break;
                        case 4:
                            counter4.incrementAndGet();
                            break;
                        case 5:
                            counter5.incrementAndGet();
                    }
                }
            }
        });

        palindrome.start();
        monoLetter.start();
        sortedStr.start();
        palindrome.join();
        monoLetter.join();
        sortedStr.join();
        System.out.printf("Красивых слов с длиной %s: %s шт\n", 3, counter3);
        System.out.printf("Красивых слов с длиной %s: %s шт\n", 4, counter4);
        System.out.printf("Красивых слов с длиной %s: %s шт\n", 5, counter5);
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}