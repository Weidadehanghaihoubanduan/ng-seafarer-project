package com.bestpay.seafarer.core.concurrent.core;

import com.bestpay.seafarer.core.concurrent.constant.HttpConstant;

import java.util.concurrent.BrokenBarrierException;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
public class Barrier {

    /**
     * Returns the number of processors available to the Java virtual machine.
     *
     * <p> This value may change during a particular invocation of the virtual
     * machine.  Applications that are sensitive to the number of available
     * processors should therefore occasionally poll this property and adjust
     * their resource usage appropriately. </p>
     */
    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors() + HttpConstant.DEFAULT;

    /**
     * Creates a new {@code CyclicBarrier} that will trip when the
     * given number of parties (threads) are waiting upon it, and which
     * will execute the given barrier action when the barrier is tripped,
     * performed by the last thread entering the barrier.
     *
     * @param parties the number of threads that must invoke {@link #await}
     *        before the barrier is tripped
     * @param barrierAction the command to execute when the barrier is
     *        tripped, or {@code null} if there is no action
     * @throws IllegalArgumentException if {@code parties} is less than 1
     */
    public static java.util.concurrent.CyclicBarrier getCyclicBarrier(int parties, Runnable barrierAction) {
        return new java.util.concurrent.CyclicBarrier(parties, barrierAction);
    }

    /**
     * cyclicBarrier.await 的委托方法
     * @param cyclicBarrier 委托对象
     */
    @SuppressWarnings("ALL")
    public static void await(java.util.concurrent.CyclicBarrier cyclicBarrier) {
        try { cyclicBarrier.await(); } catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); } }
}
