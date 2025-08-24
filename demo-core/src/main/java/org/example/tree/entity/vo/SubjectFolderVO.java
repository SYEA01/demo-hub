package org.example.tree.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Rambo
 * @create: 2025-08-24 18:09
 * @desc:
 */
@Data
public class SubjectFolderVO {
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
