package aysnc;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureTest {

    public Future<String> test1() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(2000);
            future.complete("Finish1");
            return null;
        });

        log(future.get());
        return future;
    }

    public String test2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.completedFuture("Finish2");
        log(future.get());
        return future.get();
    }

    public Future<String> test3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Finish3");
        log(future.get());
        return future;
    }

    public Future<String> test4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Finish4");
        CompletableFuture<String> thenApply = future.thenApply(s -> s + " apply");
        //CompletableFuture.supplyAsync(() -> "Finish4").thenApply(s -> s + " apply");
        log(thenApply.get());
        return thenApply;
    }

    public Future<Void> test5() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Finish5");
        CompletableFuture<Void> voidCompletableFuture = future.thenAccept(s -> System.out.println(s + " Accept"));
        return voidCompletableFuture;
    }

    public Future<Void> test6() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Finish6");
        CompletableFuture<Void> thenRun = future.thenRun(() -> System.out.println("Run Finished"));
        return thenRun;
    }

    public Future<String> test7() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Finish7")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " Compose"));
        return future;
    }

    public Future<String> test8() {
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> "Finish8.1");

        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> "Finish8.2");

        return future1.thenCombine(future2, (s1, s2) -> s1 + " + " + s2);
    }

    public String test9() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> "Future1");

        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> "Future2");

        CompletableFuture<String> future3 = CompletableFuture
                .supplyAsync(() -> "Future3");

        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(future1, future2, future3);

        log("get() : " + combinedFuture.get());
        log("future1.isDone() : " + future1.isDone());
        log("future2.isDone() : " + future2.isDone());
        log("future3.isDone() : " + future3.isDone());

        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" + "));
        log("Combined: " + combined);

        return combined;
    }

    public Future<Object> test10() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> "Future1");

        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> "Future2");

        CompletableFuture<String> future3 = CompletableFuture
                .supplyAsync(() -> "Future3");

        return CompletableFuture.anyOf(future1, future2, future3);
    }

    public Future<String> test11() {
        String name = null;

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("name is null");
            }
            return "hi, " + name;
        }).handle((s, t) -> s != null ? s : "hi unknown!");

        return future;
    }

    public Future<String> test12() {
        String name = null;

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hi, " + name);

        future.completeExceptionally(new RuntimeException("fail"));
        return future;
    }

    public Future<Void> test13() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Finish13");
        log(future.get());
        CompletableFuture<Void> finish13 = future.thenAcceptAsync(s -> log("Finish13 async"));
        return finish13;
    }

    public void log(String msg) {
        System.out.println(Thread.currentThread().getName() + "  ::  " + msg);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFutureTest a = new CompletableFutureTest();
//        System.out.println(a.test1().get());
//        System.out.println(a.test2());
//        System.out.println(a.test3().get());
//        System.out.println(a.test4().get());
//        System.out.println(a.test5().get());
//        System.out.println(a.test6().get());
//        System.out.println(a.test7().get());
//        System.out.println(a.test8().get());
//        System.out.println(a.test9());
//        System.out.println(a.test10().get());
//        System.out.println(a.test12().get());
        System.out.println(a.test13().get());
    }
}
