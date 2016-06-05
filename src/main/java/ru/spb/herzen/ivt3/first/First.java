package ru.spb.herzen.ivt3.first;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.function.Supplier;

public class First
{
    public static void main( String[] args )
    {
        ExecutorService service = Executors.newCachedThreadPool();

        Supplier<ArrayList<Double>> primeNumberSupplier = () -> {
            ArrayList<Double> list = new ArrayList<>();

            for(double number = 2; number <= 10000000; number++) {
                boolean prime = true;
                for(double divisor = 2; divisor <= Math.sqrt(number); divisor++) {
                    if (number % divisor == 0) {
                        prime = false;
                        break;
                    }
                }

                if (prime) {
                    list.add(number);
                }
            }

            return list;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(() -> primeNumberSupplier.get().size());

        service.execute(futureTask);

        try {
            System.out.println(futureTask.get());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        service.shutdown();
    }
}
