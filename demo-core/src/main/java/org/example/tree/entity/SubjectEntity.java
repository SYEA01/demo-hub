package org.example.tree.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Rambo
 * @create: 2025-08-23 22:52
 * @desc: 课题表实体
 */
@Data
public class SubjectEntity {
    // 主键id
    private Long id;
    // 课题名称
    private String title;
    // 用户id
    private Long userId;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 所属文件夹id
    private Long folderId;
}
