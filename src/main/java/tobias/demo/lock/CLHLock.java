package tobias.demo.lock;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock {


    static class CLHNode {

        private volatile boolean locked;
        private volatile String threadName = Thread.currentThread().getName();

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public String getThreadName() {
            return threadName;
        }

        public void setThreadName(String threadName) {
            this.threadName = threadName;
        }
    }


    private final AtomicReference<CLHNode> tail = new AtomicReference<>(new CLHNode());;
    private final ThreadLocal<CLHNode> predNodeThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<CLHNode> curNodeThreadLocal = ThreadLocal.withInitial(CLHNode::new);

    public void lock() {
        CLHNode curNode = curNodeThreadLocal.get();
        curNode.setLocked(true);

        CLHNode preNode = tail.getAndSet(curNode);
        predNodeThreadLocal.set(preNode);

        while (preNode.isLocked()) {
            System.out.println(Thread.currentThread().getName() + "线程的前节点" + preNode.getThreadName() + "已被lock");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "结束while");
    }

     public void unlock() {
         CLHNode curNode = curNodeThreadLocal.get();
         curNode.setLocked(false);
         System.out.println(Thread.currentThread().getName() + "线程释放了锁");
         // 由于每次lock的时候tail会设置成cur,解锁后tail依旧是cur，那么下次lock的时候需要从tail获取并设置节点时就需要获取前节点，由于都是指向cur，
         // 并且进入lock的时候会将阶段的locked属性设置未true，因为pre执行cur，所以locked属性也是true，会导致一直自旋等待，所以需要改掉当前节点引用或者新建引用
         curNodeThreadLocal.set(predNodeThreadLocal.get());
     }

    public static void main(String[] args) {
        CLHLock clhLock = new CLHLock();
        clhLock.lock();
        clhLock.unlock();
        clhLock.lock();
        clhLock.unlock();
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                clhLock.lock();
//                try {
//                    Thread.sleep(ThreadLocalRandom.current().nextInt(100));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName() + " handle done! ready to unlock");
//                clhLock.unlock();
//            }, "thread" + i).start();
//        }


    }


}
