package com.yw.entity;

import java.util.Objects;

/**
 * @author yangwei
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
