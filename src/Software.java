import java.util.concurrent.CompletableFuture;

public class Software {
    private final int PRICE;
    private final int SUPPORT;
    private final int FUNC;
    public final String name;

    public Software(String name, int price, int support, int func) {
        this.name = name;
        this.FUNC = func;
        this.PRICE = price;
        this.SUPPORT = support;
    }

    private static void simulateDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public CompletableFuture<Integer> getPrice() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            return this.PRICE;
        });
    }

    public CompletableFuture<Integer> getFunctionality() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            return this.FUNC;
        });
    }

    public CompletableFuture<Integer> getSupport() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            return this.SUPPORT;
        });
    }

}
