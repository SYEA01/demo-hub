package org.example.service;

import org.example.entity.ColumnMetadata;
import org.example.entity.IndexMetadata;
import org.example.entity.TableMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DmMetadataService {

    @Autowired
    private DataSource dataSource;

    // 获取所有表元数据
    public List<TableMetadata> getTablesMetadata() throws SQLException {
        List<TableMetadata> tables = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            // 参数说明: catalog, schemaPattern, tableNamePattern, types[]
            ResultSet rs = meta.getTables(null, "MONEYNOTE", "%", new String[]{"TABLE", "VIEW"});

            while (rs.next()) {
                TableMetadata table = new TableMetadata();
                table.setTableName(rs.getString("TABLE_NAME"));
                table.setTableComment(rs.getString("REMARKS")); // 达梦注释存储在REMARKS
                table.setTableType(rs.getString("TABLE_TYPE"));
                tables.add(table);
            }
        }
        return tables;
    }

    // 获取表的所有列元数据
    public List<ColumnMetadata> getColumnsMetadata(String tableName) throws SQLException {
        List<ColumnMetadata> columns = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            // 获取列基础信息
            ResultSet rs = meta.getColumns(null, "MONEYNOTE", tableName, "%");

            // 获取主键信息 (用于标记主键列)
            ResultSet pkRs = meta.getPrimaryKeys(null, "MONEYNOTE", tableName);
            Set<String> pkColumns = new HashSet<>();
            while (pkRs.next()) {
                pkColumns.add(pkRs.getString("COLUMN_NAME"));
            }

            while (rs.next()) {
                ColumnMetadata col = new ColumnMetadata();
                col.setColumnName(rs.getString("COLUMN_NAME"));
                col.setDataType(rs.getString("TYPE_NAME"));
                col.setColumnSize(rs.getInt("COLUMN_SIZE"));
                col.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
                col.setNullable("YES".equals(rs.getString("IS_NULLABLE")));
                col.setColumnDefault(rs.getString("COLUMN_DEF"));
                col.setColumnComment(rs.getString("REMARKS"));
                col.setPrimaryKey(pkColumns.contains(col.getColumnName()));
                // 达梦自增列需特殊判断（根据默认值包含IDENTITY）
                col.setAutoIncrement(rs.getString("COLUMN_DEF") != null
                        && rs.getString("COLUMN_DEF").contains("IDENTITY"));
                columns.add(col);
            }
        }
        return columns;
    }

    // 获取表的所有索引
    public List<IndexMetadata> getIndexesMetadata(String tableName) throws SQLException {
        List<IndexMetadata> indexes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            // 参数说明: catalog, schema, table, unique, approximate
            ResultSet rs = meta.getIndexInfo(null, "SYSDBA", tableName, false, true);

            while (rs.next()) {
                if (rs.getString("INDEX_NAME") == null) continue;

                IndexMetadata index = new IndexMetadata();
                index.setIndexName(rs.getString("INDEX_NAME"));
                index.setTableName(tableName);
                index.setColumnName(rs.getString("COLUMN_NAME"));
                index.setNonUnique(rs.getBoolean("NON_UNIQUE"));
                index.setIndexType(rs.getString("TYPE") == String.valueOf(DatabaseMetaData.tableIndexClustered)
                        ? "CLUSTERED" : "NORMAL");
                indexes.add(index);
            }
        }
        return indexes;
    }
}