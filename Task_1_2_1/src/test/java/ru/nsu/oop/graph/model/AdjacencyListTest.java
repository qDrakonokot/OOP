package ru.nsu.oop.graph.model;

class AdjacencyListTest extends GraphTestBase {
    @Override
    protected Graph<String> createGraph() {
        return new AdjacencyList<>();
    }
}