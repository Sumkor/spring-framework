package com.sumkor.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.*;

import java.util.concurrent.Callable;

/**
 * @author Sumkor
 * @since 2021/5/18
 */
public class TaskExecutorTest {

	/**
	 * 继承 JDK 的 FutureTask，提供了一个 ListenableFutureTask，用于执行回调任务
	 */
	public static void main(String[] args) {

		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.initialize(); // 必须初始化，否则报 ThreadPoolTaskExecutor not initialized

		ListenableFuture<String> listenableFuture = taskExecutor.submitListenable(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println(Thread.currentThread().getName() + " call");
				Thread.sleep(1000);
				return "hello world";
			}
		});
		listenableFuture.addCallback(new SuccessCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println(Thread.currentThread().getName() + " success: " + result);
			}
		}, new FailureCallback() {
			@Override
			public void onFailure(Throwable ex) {
				System.out.println(Thread.currentThread().getName() + " failure: " + ex.getMessage());
			}
		});
		/**
		 * 将 SuccessCallback 存入队列中
		 * @see CompletableToListenableFutureAdapter#addCallback(org.springframework.util.concurrent.SuccessCallback, org.springframework.util.concurrent.FailureCallback)
		 * @see ListenableFutureCallbackRegistry#addSuccessCallback(org.springframework.util.concurrent.SuccessCallback)
		 *
		 * 当任务执行完成，从队列中拉取 SuccessCallback 对象，执行它的 onSuccess 方法。
		 * @see FutureTask#run()
		 * @see FutureTask#finishCompletion()
		 * @see ListenableFutureTask#done()
		 * @see ListenableFutureCallbackRegistry#success(java.lang.Object)
		 *
		 *
		 * 执行结果：
		 *
		 * ThreadPoolTaskExecutor-1 call
		 * ThreadPoolTaskExecutor-1 success: hello world
		 */

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// ------------------------------------

		ListenableFutureTask<String> listenableFutureTask = new ListenableFutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println(Thread.currentThread().getName() + " call");
				return "哦豁";
			}
		});
		listenableFutureTask.addCallback(new ListenableFutureCallback<String>() {
			@Override
			public void onFailure(Throwable ex) {
				System.out.println(Thread.currentThread().getName() + " failure: " + ex.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				System.out.println(Thread.currentThread().getName() + " success: " + result);
			}
		});
		listenableFutureTask.run();
		/**
		 * 执行结果：
		 *
		 * main call
		 * main success: 哦豁
		 */
	}
}
