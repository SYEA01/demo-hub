package org.example.entity;

import lombok.Data;

@Data
public class ColumnMetadata {
    private String columnName;        // 列名
    private String dataType;          // 数据类型 (VARCHAR/NUMBER等)
    private int columnSize;           // 列长度
    private int decimalDigits;        // 小数位数
    private boolean nullable;         // 是否允许NULL
    private String columnDefault;     // 默认值
    private String columnComment;     // 列注释
    private boolean isPrimaryKey;     // 是否主键
    private boolean isAutoIncrement;  // 是否自增
}
