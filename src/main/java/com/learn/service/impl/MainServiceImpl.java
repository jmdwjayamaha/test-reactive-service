package com.learn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.service.MainService;

import rx.Observable;
import rx.schedulers.Schedulers;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private Executor threadPoolTaskExecutor;

    @Override
    public void testReactiveZip() {

        final Observable<String> operation1 = testOperation1().subscribeOn(Schedulers.newThread());
        final Observable<String> operation2 = testOperation2().subscribeOn(Schedulers.newThread());

        final Observable<String> result = Observable.zip(operation1, operation2, (operation1Resp, operation2Resp) -> {

            return operation1Resp + ":" + operation2Resp;
        });

        System.out.println("Result Received" + result.toBlocking().first());
    }

    @Override
    public void testReactiveSubscribe() {

        final List<String> lst = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            lst.add("op " + i);
        }

        System.out.println("Approach 1");
        Observable.from(lst).subscribe(item -> {

            final String threadName = Thread.currentThread().getName();
            System.out.println(item + " on: " + threadName);
        });

        System.out.println("Approach 2");
        Observable.from(lst).subscribe(item -> {

            Observable.just(item).subscribeOn(Schedulers.from(threadPoolTaskExecutor)).subscribe(itemS -> {

                final String threadName = Thread.currentThread().getName();
                System.out.println(item + " on: " + threadName);
            });
        });
    }

    private Observable<String> testOperation1() {

        return Observable.create(observer -> {

            System.out.println("Operation 1 Started");
            System.out.println("Operation 1 Thread: " + Thread.currentThread().getName());
            System.out.println("Operation 1 Thread Sleeping");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            observer.onNext("Operation 1 Completed");
            observer.onCompleted();
        });
    }

    private Observable<String> testOperation2() {

        return Observable.create(observer -> {

            System.out.println("Operation 2 Started");
            System.out.println("Operation 2 Thread: " + Thread.currentThread().getName());
            System.out.println("Operation 2 Thread Sleeping");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            observer.onNext("Operation 2 Completed");
            observer.onCompleted();
        });
    }
}