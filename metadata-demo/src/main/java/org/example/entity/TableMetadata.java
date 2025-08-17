package org.example.entity;

import lombok.Data;

@Data
public class TableMetadata {
    private String tableName;      // 表名
    private String tableComment;  // 表注释
    private String tableType;     // 表类型 (TABLE/VIEW)
}
