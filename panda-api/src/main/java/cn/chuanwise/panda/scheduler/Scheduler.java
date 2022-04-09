package cn.chuanwise.panda.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 插件任务调度器
 *
 * @author Chuanwise
 */
public interface Scheduler {
    
    /**
     * 在主线程上运行一个任务，并阻塞到返回时
     *
     * @param action 任务
     */
    void runTask(Runnable action);
    
    /**
     * 运行异步任务
     *
     * @param action 异步任务
     */
    void runAsyncTask(Runnable action);
    
    /**
     * 运行异步任务，并获取结果
     *
     * @param action 异步任务
     * @param <T> 结果类型
     * @return Future
     */
    <T> Future<T> runAsyncTask(Callable<T> action);
    
    /**
     * 关闭线程池
     */
    void shutdown();
}
