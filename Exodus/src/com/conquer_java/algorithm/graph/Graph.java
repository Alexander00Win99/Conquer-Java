package com.conquer_java.algorithm.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Graph <T> { //£ 使用<T>泛型代表MapNode图的节点 G = (V, E)
    Set<T> v = new HashSet<>();
    int[][] e;

    public Graph(Set<T> vertexs, int[][] edges) {
        v = vertexs;
        e = edges;
    }

//    public void reset() { // 复位操作：清零邻居（泛型方法实现MapNode节点清零邻居）
//        for (T t : v) {
//            t = null;
//        }
//    }
}

class Graphic { // G=(V,E)
    Set<MapNode> vertexs = new HashSet<>();
    int[][] edges;

    Graphic(Set<MapNode> vertexs, int[][] edges) {
        this.vertexs = vertexs;
        this.edges = edges;
    }

    public void resetMap() {
        for (MapNode node : vertexs) node.clearNeighbour();
    }

    static class MapNode {
        int index;
        String name;
        List<MapNode> neighbour;

        public void clearNeighbour() {
            this.neighbour = null;
        }

        public MapNode(int index, String name) {
            this.index = index;
            this.name = name;
            this.neighbour = new LinkedList<>();
        }
    }
}