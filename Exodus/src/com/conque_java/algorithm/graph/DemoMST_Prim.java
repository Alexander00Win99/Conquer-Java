package com.conque_java.algorithm.graph;

import java.util.HashSet;
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
public class DemoMST_Prim {
    // 标记普利姆算法当前步骤
    private static int countPrim = 0;

    public static void primMST(Graph graph) {
        // 任选一个节点作为起始节点(最小生成树的根节点)——choose an original node randomly by it's id
        int rand = (int) Math.floor(Math.random() * graph.v.size());
        MapNode originNode = getNodeByIndex(graph.v, rand);

        // Prim算法的结果点集
        Set<MapNode> primNodes = new HashSet<>();
        // Prim算法结果点集加入随机选择的初始节点
        primNodes.add(originNode);
        System.out.println(String.format("Prim step 0: node %d has been choosen to be the original node!", rand));
        while (true) { // 连通子图或称连通分量(Connected Component)不断扩张，直至成为极大连通子图（当结果点集大小等于图的点集大小时，代表所有节点都已连接，终止循环）
            MapNode minCostNode = getMinCostNode(graph, primNodes);
            // 连通子图或称连通分量(Connected Component)不断扩张，直至成为极大连通子图
            // 当结果点集大小等于图的点集大小时，代表所有节点都已连接，终止循环
            if (primNodes.size() == graph.v.size()) break;
        }
    }

    private static MapNode getNodeByIndex(Set<MapNode> vertexs, int index) {
        for (MapNode node : vertexs) if (node.index == index) return node;
        return null;
    }

    private static MapNode getMinCostNode(Graph graph, Set<MapNode> primNodes) {
        MapNode from = null; // 当前点集中的某个作为起点
        MapNode to = null; // 外部点集中的某个作为终点
        int minCost = Integer.MAX_VALUE;

        for (MapNode node : primNodes) { // 起点位于当前中间点集(primNodes)之中，依次取出当前primNodes点集中各个点作为起点
            for (int i = 0; i < graph.e[node.index].length; i++) { // [边集二维数组->第node.index行]==从第node.index个节点到图中其他节点的边
                MapNode thisNode = getNodeByIndex(graph.v, i);
                // 双重循环获取从当前点集到外部点集的最短边，并且边的两端互加对方作为邻居
                if (!primNodes.contains(thisNode) && graph.e[node.index][i] < minCost) { // 终点位于当前中间点集以外(vertexs - nodesPrimMST)
                    minCost = graph.e[node.index][i]; // 切割方法获取的横跨切割的轻量级边对于primNodes集合安全，并且两点之间开销最小
                    from = node;
                    to = getNodeByIndex(graph.v, i);
                }
            }
        }
        from.neighbours.add(to);
        to.neighbours.add(from);
        primNodes.add(to); // 纳入终点，中间点集(primNodes)完成扩张
        System.out.println(String.format("Prim step %d: node %d added, corresponding edge beteween node %d and node %d with cost %d added!", ++countPrim, to.index, from.index, to.index, minCost));
        return to;
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
        primMST(graph);
    }
}
