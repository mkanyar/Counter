package com.company;

public class Main {

    public static void main(String[] args) {
          //This can work here but it can't be optimized in real world since for eg we cant create an object
        //for each thread that access a bank account
//        Countdown countdown1=new Countdown();
//        Countdown countdown2=new Countdown();
        Countdown countdown=new Countdown();
	CountdownThread t1=new CountdownThread(countdown);
	t1.setName("Thread 1");
	CountdownThread t2 = new CountdownThread(countdown);
	t2.setName("Thread 2");
	t1.start();
	t2.start();
    }
}
class Countdown{
    // this i is stored on the heap, therefore if we use this the threads use the same value of i
//    that is the reason why we get lines printed since the i can be changed by both threads thus
    // if i is changed the thread that is going to access it is going to read the last value of i
    // so if they print the same value they access i when it is the same value
    private int i;
    //By adding the synchronized condition we avoid the race condition(Thread interference)
    //By adding the synchronized void doCountdown and one thread can access it at a time
    public void doCountdown(){
        String color;
        switch(Thread.currentThread().getName()){
            case "Thread 1":
                color=ThreadColor.ANSI_CYAN;
                break;
            case"Thread 2":
                color=ThreadColor.ANSI_PURPLE;
                break;
            default:
                color= ThreadColor.ANSI_GREEN;

        }
        // if we use a local variable i it is stored in the thread stack so thread 1 cant access thread's 2 stack and vice versa
        //We can synchronize a block of code that share the same info like below
        //however if we do synchronized(color) it wont work since color here is a a local variable which is stored in the thread stack
        //thus the other thread wont access it. we have to use an object that is used by both threads thus here we use this
//        synchronized (this){
            for(i=10;i>0;i--){
                System.out.println(color+Thread.currentThread().getName()+":i="+i);
            }
//        }

    }
}
class CountdownThread extends Thread{
    private Countdown threadCountdown;
    public CountdownThread(Countdown countdown){
        threadCountdown=countdown;
    }
    public void run(){
        threadCountdown.doCountdown();
    }
}