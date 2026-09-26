package ru.nsu.oop.output;

/**
 * Интерфейс описывающий контракт необходимый для вывода.
 */
public interface Output {

    /**
     * Вывести текст.
     *
     * @param text Строка для вывода.
     */
    void write(String text);
}
