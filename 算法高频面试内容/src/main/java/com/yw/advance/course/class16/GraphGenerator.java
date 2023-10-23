package com.yw.advance.course.class16;

import com.yw.entity.Edge;
import com.yw.entity.Graph;
import com.yw.entity.Vertex;

/**
 * @author yangwei
 */
public class GraphGenerator {

    // matrix 所有的边
    // N*3 的矩阵
    // [weight, from节点上面的值，to节点上面的值]
    //
    // [ 5 , 0 , 7]
    // [ 3 , 0,  1]
    public static Graph createGraph(int[][] matrix) {
        Graph g = new Graph();
        // 遍历每一条边 matrix[i]
        for (int[] x : matrix) {
            int w = x[0];                       // 权重
            Vertex from = new Vertex(x[1]);     // 创建from顶点
            Vertex to = new Vertex(x[2]);       // 创建to顶点
            Edge edge = new Edge(w, from, to);  // 创建边
            // 维护顶点其它属性信息
            from.out++;
            from.nexts.add(to);
            from.edges.add(edge);
            to.in++;
            // 将顶点和边加入到图中
            g.addVertex(from, to);
            g.addEdge(edge);
        }
        return g;
    }
}
