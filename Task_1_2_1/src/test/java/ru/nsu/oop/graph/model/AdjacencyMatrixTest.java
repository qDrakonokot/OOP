package ru.nsu.oop.graph.model;

class AdjacencyMatrixTest extends GraphTestBase {

    @Override
    protected Graph<String> createGraph() {
        return new AdjacencyMatrix<>();
    }
}