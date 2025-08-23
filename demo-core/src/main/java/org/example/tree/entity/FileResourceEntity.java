package org.example.tree.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Taoao_
 * @create: 2025-08-22 16:49
 * @desc: 测试树级目录结构实体
 */
@Data
public class FileResourceEntity {
    // id
    private Long id;

    // 文件名或者目录名
    private String title;

    // 用户id
    private Long userId;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 父级id
    private Long parentId;

    // 是否是目录
    private Boolean isDirectory;
}
