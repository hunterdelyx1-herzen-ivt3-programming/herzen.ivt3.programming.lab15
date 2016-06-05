package ru.spb.herzen.ivt3.third;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Third {
    private static ExecutorService service = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        CompletableFuture<BookPageCounter> firstBook = CompletableFuture.supplyAsync(
                () -> new BookPageCounter("J.D. Salinger: Catcher In The Rye"),
                service);

        CompletableFuture<BookPageCounter> secondBook = CompletableFuture.supplyAsync(
                () -> new BookPageCounter("Dalton Trumbo: Johnny Got His Gun"),
                service);

        CompletableFuture<BookPageCounter> pageAmountComparator = firstBook.thenCombine(secondBook, (first, second) -> {
            System.out.println(first.getName()  + " have " + first.getPageAmount()  + " words");
            System.out.println(second.getName() + " have " + second.getPageAmount() + " words");

            if (first.getPageAmount() > second.getPageAmount()) {
                return first;
            }
            return second;
        });

        try {
            System.out.println("\nSo " + pageAmountComparator.get().getName() + " have more words");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        service.shutdown();
    }

}
