package com.conque_java.algorithm.graph;

import java.util.LinkedList;
import java.util.List;

class MapNode {
    int index;
    String name;
    List<MapNode> neighbours;

    public void clearNeighbour() {
        neighbours = null;
    }

    public MapNode(int index, String name) {
        this.index = index;
        this.name = name;
        this.neighbours = new LinkedList<MapNode>();
    }
}
