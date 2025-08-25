package org.example.tree.service.impl;

import org.example.tree.convert.SubjectConvert;
import org.example.tree.entity.SubjectEntity;
import org.example.tree.entity.dto.SubjectDTO;
import org.example.tree.entity.vo.SubjectVO;
import org.example.tree.mapper.SubjectMapper;
import org.example.tree.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-23 23:12
 * @desc:
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectConvert convert;


    @Override
    public List<SubjectVO> getAllSubjects() {
        List<SubjectEntity> subjectEntities = subjectMapper.selectAll();
        return convert.entityToVoList(subjectEntities);
    }

    @Override
    public SubjectVO getById(Long id) {
        SubjectEntity subjectEntity = subjectMapper.selectById(id);
        return convert.entityToVo(subjectEntity);
    }

    @Override
    public void create(SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = convert.dtoToEntity(subjectDTO);
        subjectMapper.insert(subjectEntity);
    }

    @Override
    public void deleteById(Long id) {
        subjectMapper.deleteById(id);
    }

    @Override
    public void deleteByFolderId(Long folderId) {
        subjectMapper.deleteByFolderId(folderId);
    }
}
