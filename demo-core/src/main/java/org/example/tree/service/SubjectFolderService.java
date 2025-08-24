package org.example.tree.service;

import org.example.tree.entity.dto.SubjectFolderDTO;
import org.example.tree.entity.vo.SubjectFolderVO;

import java.util.List;

public interface SubjectFolderService {
    void create(SubjectFolderDTO subjectFolderDTO);

    void deleteById(Long id);

    void update(SubjectFolderDTO subjectFolderDTO);

    SubjectFolderVO getById(Long id);

    List<SubjectFolderVO> getAllFolders();

}
