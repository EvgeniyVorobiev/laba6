package org.example;

import java.util.Arrays;

public class Main {
    static final int size = 60000000;
    static final int halfSize = size/2;

    public static void main(String[] args) {
        Threads();
        Thread();
    }
    public static void Thread()
    {
        float[] arr = new float[size];
        Arrays.fill(arr,1.0f);
        long time = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i]*Math.sin(0.2f + i/5)*Math.cos(0.2f + i/5)*Math.cos(0.4f + i/2));
        }
        System.out.println("Время: "+(System.currentTimeMillis()-time));
        System.out.println("Первая ячейка "+arr[0]);
        System.out.println("Последняя ячейка - "+arr[size-1]);


    }
    public static void Threads()
    {
        float[] arr = new float[size];
        float[] arr1 = new float[halfSize];
        float[] arr2 = new float[halfSize];
        Arrays.fill(arr,1.0f);
        System.arraycopy(arr,0,arr1,0,halfSize);
        System.arraycopy(arr,halfSize,arr2,0,halfSize);
        long time = System.currentTimeMillis();

        Thread t = new Thread(() -> {
            for (int i = 0; i < halfSize; i++) {
                arr2[i] = (float)(arr2[i]*Math.sin(0.2f + (i+halfSize)/5)*Math.cos(0.2f + (i+halfSize)/5)*Math.cos(0.4f + (i+halfSize)/2));
            }
        });

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < halfSize; i++) {
                arr1[i] = (float)(arr1[i]*Math.sin(0.2f + i/5)*Math.cos(0.2f + i/5)*Math.cos(0.4f + i/2));
            }
        });
        t.start();
        t1.start();
        try {
            t.join();
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(arr1, 0, arr, 0, halfSize);
        System.arraycopy(arr2, 0, arr, halfSize, halfSize);

        System.out.println("Время: "+(System.currentTimeMillis()-time));
        System.out.println("Первая ячейка "+arr[0]);
        System.out.println("Последняя ячейка - "+arr[size-1]);
    }}
