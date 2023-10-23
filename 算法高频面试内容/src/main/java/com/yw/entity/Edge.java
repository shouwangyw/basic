package com.yw.entity;

import java.util.Objects;

/**
 * 边
 * @author yangwei
 */
public class Edge {
    public int w;       // 权重
    public Vertex from;
    public Vertex to;
    public Edge(int w, Vertex from, Vertex to) {
        this.w = w;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return w == edge.w &&
                Objects.equals(from, edge.from) &&
                Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(w, from, to);
    }
}
