package org.example.tree.controller;

import org.example.entity.Result;
import org.example.tree.entity.vo.SubjectVO;
import org.example.tree.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public Result<List<SubjectVO>> list() {
        return Result.success(subjectService.list());
    }


}
