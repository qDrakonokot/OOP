package ru.nsu.oop.drakonokot;
import java.util.Arrays;

public class Main {

    private static void addEl(int[] heap, int el, int heapEnd) {
        heap[heapEnd] = el;
        int curr = heapEnd;

        // sieve UP
        while (curr > 0) {
            int older = (curr - 1) / 2;

            if (heap[curr] >= heap[older]) {
                break;
            }
            else if (heap[curr] < heap[older]) {
                int tmp = heap[curr];
                heap[curr] = heap[older];
                heap[older] = tmp;
            }

            curr = older;
        }
    }

    private static int extractMin(int[] heap, int heapEnd) {
        heapEnd--;
        int min = heap[0];
        heap[0] = heap[heapEnd];

        // sieve DOWN
        int curr = 0;
        while (curr < heapEnd) {
            int minChild = 0;
            if (2 * curr + 2 >= heapEnd) {
                if (2 * curr + 1 >= heapEnd) {
                    break;
                }
                else {
                    minChild = 2 * curr + 1;
                }
            }
            else {
                minChild = heap[2 * curr + 1] < heap[2 * curr + 2] ? (2 * curr + 1) : (2 * curr + 2);
            }

            if (heap[curr] <= heap[minChild]) {
                break;
            }
            else if (heap[curr] > heap[minChild]) {
                int tmp = heap[curr];
                heap[curr] = heap[minChild];
                heap[minChild] = tmp;
            }

            curr = minChild;
        }

        return min;
    }

    private static void heapSort(int[] arr) {
        int heapEnd = 0;
        int[] heap = new int[arr.length];

        for (int el : arr) {
            addEl(heap, el, heapEnd);
            heapEnd++;
        }

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = extractMin(heap, heapEnd);
            heapEnd--;
        }
    }

    public static void main(String[] args) {

        int[] a = {0, 9, 8, 7, 6, 5, 4, 3, 2, 1, 67, 3245, 6,1 ,456 ,71, 34,5 ,1 ,324};

        heapSort(a);

        System.out.println(Arrays.toString(a));

    }
}







