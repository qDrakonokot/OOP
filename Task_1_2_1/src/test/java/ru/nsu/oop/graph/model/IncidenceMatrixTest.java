package ru.nsu.oop.graph.model;

class IncidenceMatrixTest extends GraphTestBase {

    @Override
    protected Graph<String> createGraph() {
        return new IncidenceMatrix<>();
    }
}