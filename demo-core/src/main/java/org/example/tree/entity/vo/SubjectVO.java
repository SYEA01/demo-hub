package org.example.tree.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Rambo
 * @create: 2025-08-23 23:15
 * @desc:
 */
@Data
public class SubjectVO {
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
