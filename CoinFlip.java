//For this experiment you will need to use some java functions. Specifically, System.currentTimeMillis() and Random.generate() and Random.nextInt(). The SealedDES.java program I give you in part 2 has examples. You will also need join() all your child threads from the parent thread. This is the most basic form of synchronization.
import java.util.concurrent.ThreadLocalRandom;

public class CoinFlip implements Runnable{
    static int numHeads;
    static int numTails;
    int numThreads;
    int numFlips;

    //public ThreadLocalRandom gen = new ThreadLocalRandom();

    // Constructor for Flip
    public CoinFlip(int nT, int nF) {
        this.numThreads = nT;
        this.numFlips = nF;
    }

    public void run() {
        Thread.sleep(10*numThreads);
        int headCount = 0;
        int tailCount = 0;
        for (int i = 0; i < numFlips/numThreads; i++) {
            if (ThreadLocalRandom.current().nextInt(2) == 1) {
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
            System.out.println("Usage: CoinFlip #threads #iterations");
            return;
        }
        int nT = Integer.parseInt(args[0]);
        int nF = Integer.parseInt(args[1]);

        // Create array of threads and start timer
        Thread[] threads = new Thread[nT];
        long startTime = System.currentTimeMillis();
        
        // Start threads
        for (int i = 0; i < nT; i++) {
            threads[i] = new Thread(new CoinFlip(nT, nF));
            threads[i].start();
        }

        // Join threads
        for (int i = 0; i < nT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print out information
        long runTime = System.currentTimeMillis() - startTime;
        System.out.println(numHeads + " heads in " + numFlips + " coin tosses");
        System.out.println("Elapsed time: " + runTime);
        System.out.println(numHeads + numTails);
    }
}