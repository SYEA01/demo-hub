package org.example.entity;

import lombok.Data;

@Data
public class IndexMetadata {
    private String indexName;      // 索引名称
    private String tableName;      // 所属表名
    private String columnName;     // 索引列
    private boolean nonUnique;     // 是否唯一索引
    private String indexType;      // 索引类型 (NORMAL/UNIQUE)
}
