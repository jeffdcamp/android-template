package org.jdc.template.util

import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class RxTest {
//    fun testConcurrentPeople() {
//        val pool = Executors.newFixedThreadPool(3)
//
//        RxUtil.from(Func0 { getData() })
//                // do all following calls on background thread
//                .subscribeOn(Schedulers.io())
//
//                // do some processing (fake)
//                //                .map(person -> person.getFirstName()) // single thread
//                .flatMap { person -> Observable.defer { Observable.just { person.getFirstName() }.subscribeOn(Schedulers.from(pool))} } // multi thread
//
//                // wait till all processing is done
//                .toList()
//                //                .toSortedList() // OR... wait for all to be processed and then sort
//                .flatMapIterable { list -> list }
//
//                // display on main thread
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { name -> Log.e(TAG, "Person Name: [" + name + "] thread: [" + Thread.currentThread().getName() + "]") };
//    }

    private fun getData(): List<Person> {
        val data = ArrayList<Person>()
        data.add(Person("Tom"))
        data.add(Person("Brad"))
        data.add(Person("Jenny"))
        data.add(Person("John"))
        data.add(Person("Lenny"))
        data.add(Person("Marvin"))
        return data
    }

    private class Person(private val name: String) {
        fun getFirstName(): String {
            Timber.i("PROCESSING Person: [$name] thread: [${Thread.currentThread().name}]")

            try {
                TimeUnit.SECONDS.sleep(5)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            Timber.e("Finished processing person: $name")
            return name
        }
    }
}
