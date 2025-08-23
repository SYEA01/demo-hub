package org.example.tree.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Rambo
 * @create: 2025-08-23 22:54
 * @desc: 课题所属文件夹表实体
 */
@Data
public class SubjectFolderEntity {
    // 主键id
    private Long id;
    // 文件夹名称
    private String name;
    // 父id
    private Long parentId;
    // 用户id
    private Long userId;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
}
