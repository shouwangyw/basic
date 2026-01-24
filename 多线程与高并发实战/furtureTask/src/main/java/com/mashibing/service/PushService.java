package com.mashibing.service;

import java.util.concurrent.ExecutionException;

/**
 * @author zjw
 * @description
 */
public interface PushService {

    void pushData(String date) throws ExecutionException, InterruptedException;

}
