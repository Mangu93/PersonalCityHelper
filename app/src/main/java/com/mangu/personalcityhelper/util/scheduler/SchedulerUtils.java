package com.mangu.personalcityhelper.util.scheduler;

/**
 * Created by lam on 2/6/17.
 */

public class SchedulerUtils {

    public static <T> IoMainScheduler<T> ioToMain() {
        return new IoMainScheduler<>();
    }
}
