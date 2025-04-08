package com.wj.demo.framework.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/23 11:19
 */
@Setter
@Getter
public class Node<T> {

    /**
     * 当前节点
     */
    private T data;

    /**
     * 子节点
     */
    private List<Node<T>> children;

    public Node() {
    }

    public Node(T data, List<Node<T>> children) {
        this.data = data;
        this.children = children;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NodeVO{");
        sb.append("data=").append(data);
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }
}
