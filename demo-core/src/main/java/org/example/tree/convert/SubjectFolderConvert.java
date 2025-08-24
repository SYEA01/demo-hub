package org.example.tree.convert;

import org.example.tree.entity.SubjectEntity;
import org.example.tree.entity.SubjectFolderEntity;
import org.example.tree.entity.dto.SubjectDTO;
import org.example.tree.entity.dto.SubjectFolderDTO;
import org.example.tree.entity.vo.SubjectFolderVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-24 17:43
 * @desc:
 */
@Mapper(componentModel = "spring")
public interface SubjectFolderConvert {
    //    @Mapping(target = "id", expression = "java(cn.hutool.core.util.IdUtil.getSnowflakeNextId())")
    @Mapping(source = "id", target = "id", defaultExpression = "java(cn.hutool.core.util.IdUtil.getSnowflakeNextId())")
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userId", expression = "java(getCurrentUserId())")
    SubjectFolderEntity dtoToEntity(SubjectFolderDTO dto);

    SubjectFolderVO entityToVo(SubjectFolderEntity entity);

    default Long getCurrentUserId() {
        // 这里可以从安全上下文获取当前用户ID
        // 暂时返回固定值
        return 1L;
    }

    List<SubjectFolderVO> entityToVoList(List<SubjectFolderEntity> subjectFolderEntities);
}
