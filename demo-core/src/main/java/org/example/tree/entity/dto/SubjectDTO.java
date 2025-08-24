package org.example.tree.entity.dto;

import lombok.Data;

/**
 * @author: Rambo
 * @create: 2025-08-24 15:18
 * @desc:
 */
@Data
public class SubjectDTO {
    // 课题名称
    private String title;
    // 所属文件夹id
    private Long folderId;
}
