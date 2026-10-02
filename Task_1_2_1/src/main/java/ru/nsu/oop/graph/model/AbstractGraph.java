package ru.nsu.oop.graph.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractGraph<T> implements Graph<T> {

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Graph<?>)) return false;

        @SuppressWarnings("unchecked")
        Graph<T> other = (Graph<T>) object;

        Set<T> thisVertexes = new HashSet<>(this.getVertexesList());
        Set<T> otherVertexes = new HashSet<>(other.getVertexesList());

        if (!thisVertexes.equals(otherVertexes)) return false;

        for (T vertex : thisVertexes) {
            Set<T> thisNeighbours = new HashSet<>(this.getNeighbours(vertex));
            Set<T> otherNeighbours = new HashSet<>(other.getNeighbours(vertex));

            if (!thisNeighbours.equals(otherNeighbours)) return false;
        }

        return true;
    }

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

    @Override
    public String toString() {
        List<String> formattedEdges = new ArrayList<>();

        for (T vertex : this.getVertexesList()) {
            formattedEdges.add(vertex + " -> " + this.getNeighbours(vertex));
        }

        return formattedEdges.toString();
    }
}
