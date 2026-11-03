package ru.nsu.oop.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, представляющий электронную зачетную книжку студента. Является основным хранилищем данных
 * об успеваемости. Не содержит бизнес-логики вычислений, отвечает только за целостность своих
 * данных.
 */
public class RecordBook {

    private final Map<Integer, List<Record>> semestersMap;
    private int currentSemester;
    private boolean isPaidEducation;

    /**
     * Создает новую зачетную книжку.
     *
     * @param currentSemester текущий семестр обучения (начиная с 1)
     * @param isPaidEducation true, если студент обучается на коммерческой основе, иначе false
     */
    public RecordBook(int currentSemester, boolean isPaidEducation) {
        this.semestersMap = new HashMap<>();
        this.currentSemester = currentSemester;
        this.isPaidEducation = isPaidEducation;
    }

    /**
     * Добавляет или перезаписывает список оценок за указанный семестр.
     *
     * @param semester номер семестра
     * @param records  список записей (оценок и зачетов)
     */
    public void addNewSemester(int semester, List<Record> records) {
        semestersMap.put(semester, List.copyOf(records));
    }

    /**
     * Обновляет информацию о текущем семестре студента.
     *
     * @param newSemester новый номер текущего семестра
     */
    public void updateCurrentSemester(int newSemester) {
        currentSemester = newSemester;
    }

    /**
     * Проверяет, обучается ли студент на коммерческой основе.
     *
     * @return true, если студент обучается на коммерческой основе, иначе false
     */
    public boolean isPaidEducation() {
        return isPaidEducation;
    }

    /**
     * Устанавливает флаг обучения на коммерческой основе.
     *
     * @param isPaidEducation флаг обучения
     */
    public void setPaidEducation(boolean isPaidEducation) {
        this.isPaidEducation = isPaidEducation;
    }

    /**
     * Возвращает номер текущего семестра.
     *
     * @return текущий семестр.
     */
    public int getCurrentSemester() {
        return currentSemester;
    }

    /**
     * Возвращает список всех записей за указанный семестр.
     *
     * @param semester номер семестра
     * @return неизменяемый список записей, либо пустой список, если данных за семестр нет
     */
    public List<Record> getRecordsForSemester(int semester) {
        List<Record> records = semestersMap.get(semester);
        return records != null ? List.copyOf(records) : Collections.emptyList();
    }

}
