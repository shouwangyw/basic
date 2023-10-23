package com.yw.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yangwei
 */
public class Graph {
    public Set<Vertex> vertices;   // 顶点集合
    public Set<Edge> edges;        // 边集合

    public Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }
    public void addVertex(Vertex... vs) {
        if (vs == null) return;
        this.vertices.addAll(Arrays.asList(vs));
    }
    public void addEdge(Edge... es) {
        if (es == null) return;
        this.edges.addAll(Arrays.asList(es));
    }
}
