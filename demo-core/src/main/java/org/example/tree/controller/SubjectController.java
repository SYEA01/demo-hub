package org.example.tree.controller;

import org.example.entity.Result;
import org.example.tree.entity.dto.SubjectDTO;
import org.example.tree.entity.vo.SubjectVO;
import org.example.tree.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-23 23:08
 * @desc:
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public Result<List<SubjectVO>> getAllSubjects() {
        return Result.success(subjectService.getAllSubjects());
    }

    @GetMapping("/{id}")
    public Result<SubjectVO> getById(@PathVariable Long id) {
        return Result.success(subjectService.getById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody SubjectDTO subjectDTO) {
        subjectService.create(subjectDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        subjectService.deleteById(id);
        return Result.success();
    }


}
