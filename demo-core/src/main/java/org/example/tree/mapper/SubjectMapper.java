package org.example.tree.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.tree.entity.SubjectEntity;

import java.util.List;

@Mapper
public interface SubjectMapper {
    List<SubjectEntity> selectAll();

    SubjectEntity selectById(@Param("id") Long id);

    void insert(SubjectEntity subjectEntity);

    void deleteById(@Param("id") Long id);
}
