import java.io.PrintStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleScheduledWriter implements Runnable {
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private PackageCache packageCache = PackageCache.getInstance();

    public ConsoleScheduledWriter(){
    }

    public void run() {
        System.out.println();
        for (String line : packageCache.generateCurrentOutput()) {
            System.out.println(line);
        }
    }

    public void startService() {
        executorService.scheduleWithFixedDelay(this, 15, 15, TimeUnit.SECONDS);
    }

}
