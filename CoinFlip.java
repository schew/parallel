//For this experiment you will need to use some java functions. Specifically, System.currentTimeMillis() and Random.generate() and Random.nextlong(). The SealedDES.java program I give you in part 2 has examples. You will also need join() all your child threads from the parent thread. This is the most basic form of synchronization.
import java.util.concurrent.ThreadLocalRandom;

public class CoinFlip implements Runnable{
    static long numHeads;
    static long numTails;
    static long numThreads;
    static long numFlips;

    // Constructor for Flip
    public CoinFlip(long nT, long nF) {
        this.numThreads = nT;
        this.numFlips = nF;
    }

    public void run() {
        long headCount = 0;
        long tailCount = 0;
        for (long i = 0; i < numFlips/numThreads; i++) {
            if (ThreadLocalRandom.current().nextlong(2) == 1) {
                ++headCount;
            } else {
                ++tailCount;
            }
        }
        synchronized (CoinFlip.class) {
            numHeads += headCount;
            numTails += tailCount;
        }
    }

    public static void main (String[] args) {
        // Error if user doesn't put in the right arguments
        if (args.length != 2) {
            System.out.prlongln("Usage: CoinFlip #threads #iterations");
            return;
        }
        long nT = Long.parseLong(args[0]);
        long nF = Long.parseLong(args[1]);

        // Create array of threads and start timer
        Thread[] threads = new Thread[nT];
        long startTime = System.currentTimeMillis();
        
        // Start threads
        for (long i = 0; i < nT; i++) {
            threads[i] = new Thread(new CoinFlip(nT, nF));
            threads[i].start();
        }

        // Join threads
        for (long i = 0; i < nT; i++) {
            try {
                threads[i].join();
            } catch (longerruptedException e) {
                e.prlongStackTrace();
            }
        }

        // Prlong out information
        long runTime = System.currentTimeMillis() - startTime;
        System.out.println(numHeads + " heads in " + nF + " coin tosses");
        System.out.println("Elapsed time: " + runTime);
        System.out.println(numHeads + numTails);
    }
}