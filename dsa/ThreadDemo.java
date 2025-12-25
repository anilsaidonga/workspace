import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// public class Try
// {
// 	public static void main(String[] args)
// 	{
// 		try
// 		{
// 			BufferedReader br = new BufferedReader(new FileReader("input"));
// 			String line;
// 			while ((line = br.readLine()) != null)
// 			{
// 				System.out.println(line);
// 			}
// 			br.close();
// 		}
// 		catch (IOException e)
// 		{
// 			e.printStackTrace();
// 		}
// 	}
// }

// public class Try
// {
// 	public static void main(String[] args)
// 	{
// 		System.out.println("current running thread name: " + Thread.currentThread().getName());
// 	}
// }

public class ThreadDemo {

    static final Object resource1 = new Object();
    static final Object resource2 = new Object();

    public static void main(String[] args) {

        // ✅ Multithreading: Two threads running
        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1: Starting work...");

            // Concurrency: task switching / shared resource usage
            synchronized (resource1) {
                System.out.println("Thread 1: Locked resource1");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                // Deadlock possibility
                synchronized (resource2) {
                    System.out.println("Thread 1: Locked resource2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Thread 2: Starting work...");

            synchronized (resource2) {
                System.out.println("Thread 2: Locked resource2");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                synchronized (resource1) {
                    System.out.println("Thread 2: Locked resource1");
                }
            }
        });

        // Start threads (multithreading in action)
        t1.start();
        t2.start();
    }
}
