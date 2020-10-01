package com.conquer_java.algorithm.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 在连通网的所有生成树中，所有边的代价和最小的生成树，称为最小生成树。
 * 下面的定理和推论保证了Kruskal和Prim算法的正确性，可以自己证明或参考算法导论中的说明。
 * 定理1：
 *     设G = (V, E)是一个在边E上定义了实数值权重函数w的连通无向图，设A为E的一个子集，且A包括在图G的mst中，设(S, V-S)是图中尊重A的任意一个切割，
 *     (u, v)是横跨切割(S, V-S)的一条轻量级边，那么边(u, v)对于集合A是安全的。
 * 推论1：
 *     设G = (V, E)是一个在边E上定义了实数值权重函数w的连通无向图，设A为E的一个子集，且A包括在图G的mst中，设C = (Vc, Ec)为森林GA = (V, A)中的一个连通分量（树）。
 *     如果(u, v)是连接C和GA中某个其它连通分量的一条轻量级边，则(u, v)对于集合A是安全的。
 *
 * 根据定理1和推论1，构造最小生成树可以有两种方式：一种是切割的角度；一种是森林的角度。
 * • 切割的角度——Prim算法：
 *     首先从图中选取一个点n1，划一个切割({n1}, V-{n1})，然后找这个切割的轻量级边以及这个边在 V-{n1}的节点n2；然后再划一个切割（{n1, n2},
 *     V-{n1, n2})，再找出对应的轻量级边，......，直到所有的节点都被纳入，每一步中找到的轻量级边组成的集合即为最小生成树中的边。
 * • 森林的角度——Kruskal算法：
 *     首先将每个节点都看成一个连通分量（初始拥有n个连通分量，此时每个连通分量==一个回路），寻找连通连接连通分量权值最小的边(u, v)，(u, v)连接的连通分量为C1和C2，
 *     则(u, v)连接后，合成一个更大的连通分量C12（此时有n-1个连通分量）；然后对n-1个连通分量继续实施上述类似的动作，直至最后变成一个连通分量。
 * 注：切割的方式对应的是Prim算法——加点法，森林的方式对应的是Kruskal算法——加边法。
 *
 * MST(Minimum Spanning Tree)算法
 * 1) Prim算法(加点法)：随机选取一个初始节点，依次加入最小代价节点同时避免成环(已选节点不能再次被选)，直至所有节点均被纳入形成极小连通子图；
 * 2) Kruskal算法(加边法)：每次加入当前最小代价边，同时保证不能成环(避免所选边两端节点是同一点集内部两点即可)，不断迭代，迭代过程中各个初始连通分量不断合并直至产生的最小生成树(赋权极小连通子图)；
 */
public class DemoMST_Kruskal {
    // 标记克鲁斯卡尔算法当前步骤
    private static int countKruskal = 0;

    public static void kruskalMST(Graph graph) {
        /**
         * kruskalTribes: Kruskal算法的各个相互独立的部落，部落内部相互连通
         * 初始状态：各个节点分别成为相互独立的部落（每个部落有且仅有一个成员——自身）
         * 中间状态：部落之间相互连通（互相兼并），部落数量逐渐减少
         * 终止状态：只剩一个部落存在（所有节点包含在内）
         */
        List<Set<MapNode>> kruskalTribes = new LinkedList<>();

        while (true) {
            MapNode from = null;
            MapNode to = null;
            Set<MapNode> fromTribe = null;
            Set<MapNode> toTribe = null;
            int minCost = Integer.MAX_VALUE;

            // 双重循环找出当前无向连通图中的最小边edges[i][j]，并且对于森林当前的各个连通分量来说，i和j可以连接（i和j不能同时位于同一连通分量内部）
            for (int i = 0; i < graph.e.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (i != j && graph.e[i][j] < minCost && canConnect(getNodeByIndex(graph.v, i), getNodeByIndex(graph.v, j), kruskalTribes)) {
                        from = getNodeByIndex(graph.v, i);
                        to = getNodeByIndex(graph.v, j);
                        minCost = graph.e[i][j];
                    }
                }
            }

            connect(from, to, kruskalTribes);
            System.out.println(String.format("Kruskal step %d: connect node %d to node %d, edge cost between these two nodes is %d!", ++countKruskal, from.index, to.index, minCost));
            if (kruskalTribes.size() == 1 && kruskalTribes.get(0).size() == graph.v.size()) break; // 循环出口：当前连通分量（部落）只有一个
        }
    }

    private static MapNode getNodeByIndex(Set<MapNode> vertexs, int index) {
        for (MapNode node : vertexs) if (node.index == index) return node;
        return null;
    }

    private static boolean canConnect(MapNode from, MapNode to, List<Set<MapNode>> kruskalTribes) {
        Set<MapNode> fromTribe = getTribeByNode(kruskalTribes, from);
        Set<MapNode> toTribe = getTribeByNode(kruskalTribes, to);
        if (fromTribe != null && toTribe != null && fromTribe == toTribe) return false;
        return true;
    }

    private static Set<MapNode> getTribeByNode(List<Set<MapNode>> kruskalTribes, MapNode node) {
        for (Set<MapNode> connectComponent : kruskalTribes)
            if (connectComponent.contains(node))
                return connectComponent;
        return null;
    }

    private static void connect(MapNode from, MapNode to, List<Set<MapNode>> kruskalTribes) {
        Set<MapNode> fromTribe = getTribeByNode(kruskalTribes, from);
        Set<MapNode> toTribe = getTribeByNode(kruskalTribes, to);
        // 1) 起点和终点均不属于当前某个已有部落，新建一个部落，加入两点
        if (fromTribe == null && toTribe == null) {
            Set<MapNode> newTribe = new HashSet<>();
            newTribe.add(from);
            newTribe.add(to);
            kruskalTribes.add(newTribe);
            return;
        }
        // 2) 起点在终点不在当前某个已有部落里，终点加入起点部落
        if (fromTribe != null && toTribe == null) {
            fromTribe.add(to);
            return;
        }
        // 3) 起点不在终点在当前某个已有部落里，起点加入终点部落
        if (fromTribe == null && toTribe != null) {
            toTribe.add(from);
            return;
        }
        // 4) 起点和终点均在当前某个已有部落里，并且两个部落不同，合并两个部落(留下任意一个同时删除另外一个)
        if (fromTribe != null && toTribe != null && fromTribe != toTribe) {
            fromTribe.addAll(toTribe);
            kruskalTribes.remove(toTribe);
            //toTribe.addAll(fromTribe);
            //kruskalTribes.remove(fromTribe);
            return;
        }
        // 5) 起点和终点均在当前某个已有部落里，并且两个部落相同，不能连接两点，否则成环
        if (fromTribe != null && toTribe != null && fromTribe == toTribe) {
            return;
        }
    }

    public static void main(String[] args) {
        MapNode a = new MapNode(0, "A");
        MapNode b = new MapNode(1, "B");
        MapNode c = new MapNode(2, "C");
        MapNode d = new MapNode(3, "D");
        MapNode e = new MapNode(4, "E");
        MapNode f = new MapNode(5, "F");
        // 点集(包含所有节点)
        Set<MapNode> vertexs = new HashSet<>();
        vertexs.add(a);
        vertexs.add(b);
        vertexs.add(c);
        vertexs.add(d);
        vertexs.add(e);
        vertexs.add(f);
        // 边集(稀疏矩阵采用三元组或者邻接矩阵；密集矩阵采用二维数组)
        int[][] edges = { // i二维数组边集：nt[i][j]表示边：[第index=i个节点-第index=j个节点]
                {0, 3, 4, Integer.MAX_VALUE, Integer.MAX_VALUE, 31},
                {3, 0, Integer.MAX_VALUE, 5, 13, Integer.MAX_VALUE},
                {4, Integer.MAX_VALUE, 0, 22, 16, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 5, 22, 0, Integer.MAX_VALUE, 25},
                {Integer.MAX_VALUE, 13, 16, Integer.MAX_VALUE, 0, 9},
                {31, Integer.MAX_VALUE, Integer.MAX_VALUE,25, 9, 0}
        };
        // 点集 + 边集 = 图
        Graph graph = new Graph<MapNode>(vertexs, edges);
        kruskalMST(graph);

//        C语言如下声明二维数组，JAVA如下则会报错“edges未初始化”
//        int[][] edges;
//        for (int i = 0; i < vertexs.size(); i ++) {
//            for (int j = 0; j < vertexs.size(); j ++) {
//                edges[i][j] = Integer.MAX_VALUE;
//            }
//        }
//        edges[0][1] = 2; edges[0][2] = 3; edges[0][3] = 4; edges[0][4] = 9;
//        edges[1][0] = 2; edges[1][3] = 4; edges[1][6] = 16;
//        edges[2][0] = 3; edges[2][4] = 5; edges[2][5] = 25;
//        edges[3][0] = 4; edges[3][1] = 4; edges[3][4] = 1; edges[3][5] = 6;
//        edges[4][0] = 9; edges[4][2] = 5; edges[4][3] = 1; edges[4][6] = 7;
//        edges[5][2] = 25; edges[5][3] = 6;
//        edges[6][1] = 16; edges[6][4] = 7;
    }
}
