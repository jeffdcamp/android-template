package org.jdc.template.util;

import android.util.Log;

import org.jdc.template.App;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxTest {
    public static final String TAG = App.createTag(RxTest.class);

    public static void testConcurrentPeople() {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        RxUtil.from(() -> getData())
                // do all following calls on background thread
                .subscribeOn(Schedulers.io())

                 // do some processing (fake)
//                .map(person -> person.getFirstName()) // single thread
                .flatMap(person -> Observable.defer(() -> Observable.just(person.getFirstName())).subscribeOn(Schedulers.from(pool))) // multi thread

                // wait till all processing is done
                .toList()
//                .toSortedList() // OR... wait for all to be processed and then sort
                .flatMapIterable(list -> list)

                // display on main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(name -> Log.e(TAG, "Person Name: [" + name + "] thread: [" + Thread.currentThread().getName() + "]"));
    }

    private static List<Person> getData() {
        List<Person> data = new ArrayList<>();
        data.add(new Person("Tom"));
        data.add(new Person("Brad"));
        data.add(new Person("Jenny"));
        data.add(new Person("John"));
        data.add(new Person("Lenny"));
        data.add(new Person("Marvin"));
        return data;
    }

    private static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }


        public String getFirstName() {
            Log.e(TAG, "PROCESSING Person: [" + name + "] thread: [" + Thread.currentThread().getName() + "]");

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "Finished processing person: " + name);
            return name;
        }
    }
}
