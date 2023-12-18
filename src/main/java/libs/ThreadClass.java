package libs;


import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ThreadClass extends Thread{


    protected abstract void threadMain();

    public enum EThreadStatus {
        THREAD_INACTIVE,
        THREAD_ACTIVE,
        THREAD_PAUSE,
        THREAD_STOP
    }

    private static final int THREAD_NAME_MAX = 16;

    private Thread mThread;
    private String mName;
    private Lock mMutex = new ReentrantLock();

    @Setter
    @Getter
    private EThreadStatus mThreadStatus;

    public ThreadClass (String name){
        this.mThread = null;
        this.mName = name;
        this.mMutex = new ReentrantLock();
        this.mThreadStatus = EThreadStatus.THREAD_INACTIVE;
        if (name != null)
            this.mName = name.substring(0, Math.min(name.length(), THREAD_NAME_MAX - 1));
    }


    public void run(){
        threadMain();
    }

    public boolean startThread(){
        if (mThread == null || !mThread.isAlive()){
            try {
                mThread = new Thread(this::run);
                mThread.start();
            }
            catch (Exception e) {
                mThread = null;
                return false;
            }
        }
        if (mName != null){
            mThread.setName(mName);
        }
        return true;
    }

    public boolean stopThread() {
        if (mThread != null && mThread.isAlive()) {
            mThreadStatus = EThreadStatus.THREAD_INACTIVE;
            try {
                mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mThread = null;
        return true;
    }

    public void closeThread (){
        mThread = null;
    }
}
