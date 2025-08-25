package org.example.tree.controller;

import org.example.entity.Result;
import org.example.tree.entity.dto.SubjectFolderDTO;
import org.example.tree.entity.vo.SubjectFolderVO;
import org.example.tree.service.SubjectFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-24 17:38
 * @desc:
 */
@RestController
@RequestMapping("/subject-folder")
public class SubjectFolderController {
    @Autowired
    private SubjectFolderService subjectFolderService;

    @PostMapping
    public Result<Void> create(@RequestBody SubjectFolderDTO subjectFolderDTO) {
        subjectFolderService.create(subjectFolderDTO);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody SubjectFolderDTO subjectFolderDTO) {
        subjectFolderService.update(subjectFolderDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SubjectFolderVO> getById(@PathVariable Long id) {
        return Result.success(subjectFolderService.getById(id));
    }

    @GetMapping
    public Result<List<SubjectFolderVO>> getAllFolders() {
        return Result.success(subjectFolderService.getAllFolders());
    }

    @DeleteMapping("/{folderId}")
    public Result<Void> deleteFolder(@PathVariable Long folderId) {
        subjectFolderService.deleteFolder(folderId);
        return Result.success();
    }

}
