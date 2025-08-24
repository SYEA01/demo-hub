package org.example.tree.service.impl;

import org.example.tree.convert.SubjectFolderConvert;
import org.example.tree.entity.SubjectFolderEntity;
import org.example.tree.entity.dto.SubjectFolderDTO;
import org.example.tree.entity.vo.SubjectFolderVO;
import org.example.tree.mapper.SubjectFolderMapper;
import org.example.tree.service.SubjectFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-24 17:40
 * @desc:
 */
@Service
public class SubjectFolderServiceImpl implements SubjectFolderService {

    @Autowired
    private SubjectFolderMapper subjectFolderMapper;

    @Autowired
    private SubjectFolderConvert convert;

    @Override
    public void create(SubjectFolderDTO subjectFolderDTO) {
        SubjectFolderEntity subjectFolderEntity = convert.dtoToEntity(subjectFolderDTO);
        subjectFolderMapper.insert(subjectFolderEntity);
    }

    @Override
    public void deleteById(Long id) {
        subjectFolderMapper.deleteById(id);
    }

    @Override
    public void update(SubjectFolderDTO subjectFolderDTO) {
        SubjectFolderEntity subjectFolderEntity = convert.dtoToEntity(subjectFolderDTO);
        subjectFolderMapper.update(subjectFolderEntity);
    }

    @Override
    public SubjectFolderVO getById(Long id) {
        SubjectFolderEntity subjectFolderEntity = subjectFolderMapper.selectById(id);
        return convert.entityToVo(subjectFolderEntity);
    }

    @Override
    public List<SubjectFolderVO> getAllFolders() {
        List<SubjectFolderEntity> subjectFolderEntities = subjectFolderMapper.selectAll();
        return convert.entityToVoList(subjectFolderEntities);
    }
}
