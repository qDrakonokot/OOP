package ru.nsu.oop.actor;

import ru.nsu.oop.view.GameView;

/**
 * Класс Дилера с автоматизированной игровой логикой.
 */
public class Dealer extends Participant {

    public Dealer() {
        super("Дилер");
    }

    /**
     * Принимает решение по правилам казино: дилер берет карту, пока счет меньше 17.
     *
     * @param view Игнорируется (дилеру не нужен интерфейс).
     * @return true, если счет < 17, иначе false.
     */
    @Override
    public boolean makeDecision(GameView view) {
        return getScore() < 17;
    }
}
