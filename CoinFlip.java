//For this experiment you will need to use some java functions. Specifically, System.currentTimeMillis() and Random.generate() and Random.nextlong(). The SealedDES.java program I give you in part 2 has examples. You will also need join() all your child threads from the parent thread. This is the most basic form of synchronization.
import java.util.concurrent.ThreadLocalRandom;

public class CoinFlip implements Runnable{
    long headCount;
    long numThreads;
    long numFlips;
    long tailCount;

    // Constructor for Flip
    public CoinFlip(long nT, long nF) {
        this.numThreads = nT;
        this.numFlips = nF;
    }

    public long getHeads() {
        return this.headCount;
    }

    public long getTails() {
        return tailCount;
    }

    public void run() {
        headCount = 0;
        tailCount = 0;
        for (long i = 0; i < numFlips/numThreads; i++) {
            if (ThreadLocalRandom.current().nextInt(2) == 1) {
                ++headCount;
            } else {
                ++tailCount;
            }
        }
System.out.println("run " + headCount);
    }

    public static void main (String[] args) {
//        long startTime = System.currentTimeMillis();
        // Error if user doesn't put in the right arguments
        if (args.length != 2) {
            System.out.println("Usage: CoinFlip #threads #iterations");
            return;
        }
        long nT = Long.parseLong(args[0]);
        long nF = Long.parseLong(args[1]);

        long numHeads = 0;
        long numTails = 0;

        // Create array of threads and start timer
        Thread[] threads = new Thread[(int)nT];
        CoinFlip[] flippers = new CoinFlip[(int)nT];
        long startTime = System.currentTimeMillis();
        // Start threads
        for (int i = 0; i < nT; i++) {
            flippers[i] = new CoinFlip(nT, nF);
            threads[i] = new Thread(flippers[i]);
            threads[i].start();
        }
        //long runTime = System.currentTimeMillis() - startTime;

        // Join threads
        for (int i = 0; i < nT; i++) {
            try {
System.out.println(flippers[i].getHeads() + " flippers");
                numHeads += flippers[i].getHeads();
                numTails += flippers[i].getTails();
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long runTime = System.currentTimeMillis() - startTime;

        // Print out information
        System.out.println(numHeads + " heads in " + nF + " coin tosses");
        System.out.println("Elapsed time: " + runTime);
        System.out.println(numHeads + numTails);
    }
}
