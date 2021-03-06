/*
 * The MIT License
 *
 * Copyright 2017 Intuit Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.intuit.karate.cucumber;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pthomas3
 */
public class KarateStats {
    
    private int testCount;
    private int failCount;
    private int skipCount;
    private double timeTaken;    
    private final long startTime;
    private long endTime;
    private List<String> failedList;
    private Throwable failureReason;
    
    private KarateStats(long startTime) {
        this.startTime = startTime;
    }
    
    public void addToFailedList(String name) {
        if (failedList == null) {
            failedList = new ArrayList<>();
        }
        failedList.add(name);
    }
    
    public static KarateStats startTimer() {
        return new KarateStats(System.currentTimeMillis());
    }

    public void setFailureReason(Throwable failureReason) {
        this.failureReason = failureReason;
    }

    public Throwable getFailureReason() {
        return failureReason;
    }        
    
    public void addToTestCount(int count) {
        testCount += count;
    }
    
    public void addToFailCount(int count) {
        failCount += count;
    }
    
    public void addToSkipCount(int count) {
        skipCount += count;
    }
    
    public void addToTimeTaken(double time) {
        timeTaken += time;
    }
    
    public void stopTimer() {
        endTime = System.currentTimeMillis();
    }
    
    public void printStats(int threadCount) {
        double elapsedTime = endTime - startTime;
        System.out.println("=========================================================");
        System.out.println(String.format("elapsed time: %f | test time: %f", elapsedTime / 1000, timeTaken));
        double efficiency = 1000 * timeTaken / (elapsedTime * threadCount);
        System.out.println(String.format("thread count: %2d | parallel efficiency: %f", threadCount, efficiency));
        System.out.println(String.format("scenarios: %3d | failed: %3d | skipped: %3d", testCount, failCount, skipCount));
        System.out.println("=========================================================");
        if (failedList != null) {
            System.out.println("failed: " + failedList);
        }
        if (failureReason != null) {
            System.out.println("*** runner exception stack trace ***");
            failureReason.printStackTrace();
        }
    }

    public int getTestCount() {
        return testCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public int getSkipCount() {
        return skipCount;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public List<String> getFailedList() {
        return failedList;
    }        
    
}
