package org.example.tree.entity.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-24 20:35
 * @desc:
 */
@Data
public class TreeNode {

    // id
    private Long entityId;

    // 节点名称：文件夹名 或者 课题标题
    private String entityName;

    // 节点类型：'folder' 或 'subject'
    private NodeType type;

    // 父节点ID
    private Long parentId;

    // 原始实体对象
    private Object data;

    // 子节点列表
    private List<TreeNode> children = new ArrayList<>();

    public boolean isFolder() {
        return type == NodeType.FOLDER;
    }

    public boolean isSubject() {
        return type == NodeType.SUBJECT;
    }

    public Boolean isLeaf() {
        return type == NodeType.SUBJECT;
    }

    public enum NodeType {
        FOLDER,
        SUBJECT;

        @JsonValue
        public String toValue() {
            return name().toLowerCase();
        }
    }
}

