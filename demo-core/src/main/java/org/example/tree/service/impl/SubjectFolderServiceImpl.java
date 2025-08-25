package org.example.tree.service.impl;

import org.example.tree.convert.SubjectFolderConvert;
import org.example.tree.entity.SubjectFolderEntity;
import org.example.tree.entity.dto.SubjectFolderDTO;
import org.example.tree.entity.vo.SubjectFolderVO;
import org.example.tree.mapper.SubjectFolderMapper;
import org.example.tree.service.SubjectFolderService;
import org.example.tree.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private SubjectService subjectService;

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

    @Override
    public void deleteFolder(Long folderId) {
        // 删除这个文件夹
        subjectFolderMapper.deleteById(folderId);

        // 删除所有课题
        subjectService.deleteByFolderId(folderId);

        // 查询这个文件夹下还有哪些文件夹
        List<SubjectFolderEntity> folders = subjectFolderMapper.selectByParentId(folderId);
        if (!CollectionUtils.isEmpty(folders)){
            for (SubjectFolderEntity folder : folders) {
                // 递归删除
                deleteFolder(folder.getId());
            }
        }

    }
}
