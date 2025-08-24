package org.example.tree.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.tree.entity.SubjectFolderEntity;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-24 17:40
 * @desc:
 */
@Mapper
public interface SubjectFolderMapper {

    void insert(SubjectFolderEntity subjectFolderEntity);

    void deleteById(@Param("id") Long id);

    void update(SubjectFolderEntity subjectFolderEntity);

    SubjectFolderEntity selectById(@Param("id") Long id);

    List<SubjectFolderEntity> selectAll();
}
