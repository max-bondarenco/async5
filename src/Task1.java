import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Task1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException _) {}
            return "Task 1 result";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException _) {}
            return "Task 2 result";
        });

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2);
        allTasks.thenRun(() -> System.out.println("Усі завдання завершені\n"+task1.getNow("")+"\n"+task2.getNow(""))).get();
    }
}
