import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class Task2 {
    private static StringBuffer bestSoftware = null;
    private static final AtomicInteger bestScore = new AtomicInteger(Integer.MIN_VALUE);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Software[] softwareOptions = {
                new Software("Soft 1", 7, 5, 9),
                new Software("Soft 2",1, 3, 4),
                new Software("Soft 3",10, 9, 10)
        };

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Software software : softwareOptions) {
            futures.add(CompletableFuture.runAsync(() -> {
                CompletableFuture<Integer> priceFuture = software.getPrice();
                CompletableFuture<Integer> functionalityFuture = software.getFunctionality();
                CompletableFuture<Integer> supportFuture = software.getSupport();

                try {
                    int score = priceFuture
                            .thenCombine(functionalityFuture, (price, functionality) -> 11 - price + functionality)
                            .thenCombine(supportFuture, Integer::sum)
                            .get();

                    if (score > bestScore.get()) {
                        bestScore.set(score);
                        synchronized (bestScore) {
                            bestSoftware = new StringBuffer(software.name);
                        }
                    }

                    System.out.printf("Software: %s, Score: %d%n", software.name, score);

                } catch (InterruptedException | ExecutionException _) {}
            }));
        }

        futures.stream().reduce(CompletableFuture.allOf(),(val,acc) -> CompletableFuture.allOf(acc,val)).join();

        System.out.printf("Best Software: %s with Score: %d%n", bestSoftware, bestScore.get());
    }
}