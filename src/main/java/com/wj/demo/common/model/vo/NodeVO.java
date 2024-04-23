package com.wj.demo.common.model.vo;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/23 11:19
 */
public class NodeVO<T> {

    /**
     * 当前节点
     */
    private T data;

    /**
     * 子节点
     */
    private List<NodeVO<T>> children;

    public NodeVO() {
    }

    public NodeVO(T data, List<NodeVO<T>> children) {
        this.data = data;
        this.children = children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<NodeVO<T>> getChildren() {
        return children;
    }

    public void setChildren(List<NodeVO<T>> children) {
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
