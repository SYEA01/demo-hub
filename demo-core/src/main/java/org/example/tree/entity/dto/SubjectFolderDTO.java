package org.example.tree.entity.dto;

import lombok.Data;

/**
 * @author: Rambo
 * @create: 2025-08-24 17:42
 * @desc:
 */
@Data
public class SubjectFolderDTO {
    private Long id;
    // 文件夹名称
    private String name;
    // 父id
    private Long parentId;
}
