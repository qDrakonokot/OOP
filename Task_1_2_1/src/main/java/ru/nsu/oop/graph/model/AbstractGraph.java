package ru.nsu.oop.graph.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Скелетная реализация интерфейса {@link Graph}. Предоставляет универсальные методы сравнения и
 * строкового представления, которые работают независимо от внутреннего способа хранения графа.
 *
 * @param <T> тип данных вершин
 */
public abstract class AbstractGraph<T> implements Graph<T> {

    /**
     * Сравнивает два графа на логическое равенство. Графы считаются равными, если они содержат
     * одинаковое множество вершин и одинаковое множество связей (ребер) для каждой вершины,
     * независимо от их внутреннего представления (матрица или список).
     *
     * @param object объект для сравнения
     * @return true, если графы логически идентичны, иначе false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Graph<?>)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Graph<T> other = (Graph<T>) object;

        Set<T> thisVertexes = new HashSet<>(this.getVertexesList());
        Set<T> otherVertexes = new HashSet<>(other.getVertexesList());

        if (!thisVertexes.equals(otherVertexes)) {
            return false;
        }

        for (T vertex : thisVertexes) {
            Set<T> thisNeighbours = new HashSet<>(this.getNeighbours(vertex));
            Set<T> otherNeighbours = new HashSet<>(other.getNeighbours(vertex));

            if (!thisNeighbours.equals(otherNeighbours)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Вычисляет хеш-код графа. Хеш-код не зависит от порядка добавления вершин и ребер.
     *
     * @return числовое значение хеш-кода
     */
    @Override
    public int hashCode() {
        int hash = 0;

        for (T vertex : this.getVertexesList()) {
            int vertexHash = vertex.hashCode();
            int neighboursHash = new HashSet<>(this.getNeighbours(vertex)).hashCode();

            hash += (vertexHash ^ neighboursHash);
        }

        return hash;
    }

    /**
     * Возвращает строковое представление графа в виде списка смежности. Формат: [Вершина1 ->
     * [Сосед1, Сосед2], Вершина2 -> []]
     *
     * @return отформатированная строка
     */
    @Override
    public String toString() {
        List<String> formattedEdges = new ArrayList<>();

        for (T vertex : this.getVertexesList()) {
            formattedEdges.add(vertex + " -> " + this.getNeighbours(vertex));
        }

        return formattedEdges.toString();
    }
}
