import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadTest {

	final static ExecutorService exec = Executors.newCachedThreadPool();
	protected static Lock loginLock = new ReentrantReadWriteLock().writeLock();

	public static void main(String args[]) {
		class TestThread implements Runnable {
			String name;

			public TestThread(String name) {
				super();
				this.name = name;
			}

			public void run() {
				System.out.println(name + " run ");
				boolean falg = false;
				try {
					System.out.println(name+"\t"+Thread.currentThread().getName());
					falg = loginLock.tryLock(3, TimeUnit.SECONDS);
					if(falg)
					{
						System.out.println("secod ge lock "+loginLock.tryLock(3, TimeUnit.SECONDS));
						TimeUnit.SECONDS.sleep(10);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (falg) {
						loginLock.unlock();
					}
				}
				System.out.println(name + " runend ");
			}
		}
		try {
			Runnable a = new TestThread("aa");
			Runnable b = new TestThread("bb");
			exec.submit(a);
			exec.submit(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}