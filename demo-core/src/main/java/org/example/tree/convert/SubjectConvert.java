package org.example.tree.convert;

import org.example.tree.entity.SubjectEntity;
import org.example.tree.entity.vo.SubjectVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-23 23:27
 * @desc: 配置转换器
 */
@Mapper(componentModel = "spring")   // 让 Spring 管理
public interface SubjectConvert {
    SubjectVO entityToVO(SubjectEntity entity);

    List<SubjectVO> entityToVOList(List<SubjectEntity> entities);

}
