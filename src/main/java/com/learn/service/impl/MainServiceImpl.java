package com.learn.service.impl;

import org.springframework.stereotype.Service;

import com.learn.service.MainService;

import rx.Observable;
import rx.schedulers.Schedulers;

@Service
public class MainServiceImpl implements MainService {

    @Override
    public void testReactiveZip() {

        final Observable<String> operation1 = testOperation1().subscribeOn(Schedulers.newThread());
        final Observable<String> operation2 = testOperation2().subscribeOn(Schedulers.newThread());

        final Observable<String> result = Observable.zip(operation1, operation2, (operation1Resp, operation2Resp) -> {

            return operation1Resp + ":" + operation2Resp;
        });

        /*
         * result.toList().forEach(str -> { System.out.println(str); });
         */

        System.out.println("Result Received" + result.toBlocking().first());
    }

    private Observable<String> testOperationAsync(final int value) {

        return Observable.create(observer -> {

            System.out.println("Operation " + value + " Started");
            System.out.println("Operation " + value + " Thread: " + Thread.currentThread().getName());
            System.out.println("Operation " + value + " Thread Sleeping");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            observer.onNext("Operation " + value + " Completed");
            observer.onCompleted();
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