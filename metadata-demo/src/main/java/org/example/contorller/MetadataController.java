package org.example.contorller;

import org.example.entity.ColumnMetadata;
import org.example.entity.TableMetadata;
import org.example.service.DmMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    @Autowired
    private DmMetadataService metadataService; 

    @GetMapping("/tables")
    public List<TableMetadata> listTables() throws SQLException {
        return metadataService.getTablesMetadata();
    }

    @GetMapping("/columns/{tableName}")
    public List<ColumnMetadata> listColumns(@PathVariable String tableName) throws SQLException {
        return metadataService.getColumnsMetadata(tableName);
    }
}
