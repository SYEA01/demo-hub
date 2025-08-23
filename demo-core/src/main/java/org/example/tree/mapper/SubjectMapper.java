package org.example.tree.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.tree.entity.SubjectEntity;

import java.util.List;

@Mapper
public interface SubjectMapper {
    List<SubjectEntity> selectAll();
}
