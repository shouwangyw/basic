package com.yw.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 顶点
 * @author yangwei
 */
public class Vertex {
    public int val;             // 顶点值
    public int in;              // 入度
    public int out;             // 出度
    public List<Vertex> nexts;  // 从当前节点出发的所有邻居节点集合
    public List<Edge> edges;    // 从当前节点出发的所有边的集合
    public Vertex(int val) {
        this.val = val;
        this.in = 0;
        this.out = 0;
        this.nexts = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return val == vertex.val &&
                in == vertex.in &&
                out == vertex.out &&
                Objects.equals(nexts, vertex.nexts) &&
                Objects.equals(edges, vertex.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, in, out, nexts, edges);
    }
}
