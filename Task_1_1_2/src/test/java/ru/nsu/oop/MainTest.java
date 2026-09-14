package ru.nsu.oop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class MainTest {

    private final InputStream originalIn = System.in;

    @AfterEach
    void tearDown() {
        // Обязательно возвращаем консоль в норму после теста
        System.setIn(originalIn);
    }

    @Test
    void testMainExecution() {
        // Отправляем стопку нулей.
        // Первый "0" остановит добор карт игрока.
        // Второй "0" скажет "нет" на предложение сыграть еще раз, что прервет while(true).
        // Запасные нули нужны на случай мгновенного блэкджека со старта.
        String simulatedInput = "0\n0\n0\n0\n0\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Запускаем приложение. Оно должно отработать один цикл и спокойно завершиться.
        Main.main(new String[]{});
    }
}