package aysnc;

public class AsyncRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("run!");
    }

    public static void main(String[] args) {
        new Thread(new AsyncRunnable()).start();

        AsyncRunnable asyncRunnable = new AsyncRunnable();
        asyncRunnable.run();

        new HelloThread().start();
    }
}
