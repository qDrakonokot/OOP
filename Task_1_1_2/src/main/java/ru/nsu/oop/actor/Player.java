package ru.nsu.oop.actor;

import ru.nsu.oop.view.GameView;

public class Player extends Participant {

    public Player() {
        super("Вы");
    }

    @Override
    public boolean makeDecision(GameView view) {
        return view.askPlayerMove();
    }
}