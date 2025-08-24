package org.example.tree.convert;

import org.example.tree.entity.SubjectEntity;
import org.example.tree.entity.dto.SubjectDTO;
import org.example.tree.entity.vo.SubjectVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-23 23:27
 * @desc: 配置转换器
 */
@Mapper(componentModel = "spring")   // 让 Spring 管理
public interface SubjectConvert {
    SubjectVO entityToVo(SubjectEntity entity);

    List<SubjectVO> entityToVoList(List<SubjectEntity> entities);

    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(cn.hutool.core.util.IdUtil.getSnowflakeNextId())")
    @Mapping(target = "userId", expression = "java(getCurrentUserId())")
    SubjectEntity dtoToEntity(SubjectDTO dto);

    default Long getCurrentUserId() {
        // 这里可以从安全上下文获取当前用户ID
        // 暂时返回固定值
        return 1L;
    }


}
