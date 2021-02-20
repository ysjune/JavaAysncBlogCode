package aysnc;

import java.util.concurrent.*;

public class FutureService {

    public void future() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> reference = executorService.submit(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "Hello";
                    }
                }
        );

        if(reference.isDone()) {
            System.out.println(reference.get());
        }
    }

    public void futureTask() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        FutureTask<String> reference = new FutureTask<>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "Hello";
                    }
                }
        );

        executorService.execute(reference);
    }
}
