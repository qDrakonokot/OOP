package ru.nsu.oop.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordBook {

    private final Map<Integer, List<Record>> semestersMap;
    private int currentSemester;
    private boolean isPaidEducation;

    public RecordBook(int currentSemester, boolean isPaidEducation) {
        this.semestersMap = new HashMap<>();
        this.currentSemester = currentSemester;
        this.isPaidEducation = isPaidEducation;
    }

    public void addNewSemester(int semester, List<Record> records) {
        semestersMap.put(semester, records);
    }

    // Я пока не знаю какие методы еще могут пригодиться.
}
