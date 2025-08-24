package org.example.tree.service;

import org.example.tree.entity.dto.SubjectDTO;
import org.example.tree.entity.vo.SubjectVO;

import java.util.List;

public interface SubjectService {
    List<SubjectVO> getAllSubjects();

    SubjectVO getById(Long id);

    void create(SubjectDTO subjectDTO);

    void deleteById(Long id);
}
