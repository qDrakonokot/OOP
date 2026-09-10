package ru.nsu.oop.drakonokot;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Класс для тестирования сортировки. Содержит минимальный набор тестов, покрывающий граничные
 * случаи.
 */
public class HeapSortTest {

    @Test
    void testEmptyArray() {
        int[] arr = {};
        Main.heapSort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        Main.heapSort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        Main.heapSort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        Main.heapSort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        Main.heapSort(arr);
        int[] expected = {1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9};
        assertArrayEquals(expected, arr);
    }

    @Test
    void testLargeRandomArray() {
        int[] arr = new int[10000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100000);
        }
        int[] copy = arr.clone();
        Main.heapSort(arr);
        for (int i = 0; i < arr.length - 1; i++) {
            assertTrue(arr[i] <= arr[i + 1]);
        }

        java.util.Arrays.sort(copy);
        assertArrayEquals(copy, arr);
    }

    @Test
    void measureSortingTime() {
        int[] sizes = {1000, 2000, 5000, 10000, 20000, 50000, 100000};
        System.out.println("n\t\ttime(ms)");
        for (int n : sizes) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = (int) (Math.random() * n);
            }
            long start = System.nanoTime();
            Main.heapSort(arr);
            long end = System.nanoTime();
            double ms = (end - start) / 1_000_000.0;
            System.out.printf("%d\t\t%.2f\n", n, ms);
        }
    }

    @Test
    void testMainMethod() {
        // Проверяем, что main отрабатывает без исключений, иначе Якоко шлет на...
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

}
