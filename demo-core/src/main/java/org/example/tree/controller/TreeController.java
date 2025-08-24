package org.example.tree.controller;

import org.example.entity.Result;
import org.example.tree.entity.vo.TreeNode;
import org.example.tree.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Rambo
 * @create: 2025-08-24 20:57
 * @desc:
 */
@RestController
@RequestMapping("/tree")
public class TreeController {
    @Autowired
    private TreeService treeService;

    @GetMapping("/subject-tree")
    public Result<List<TreeNode>> getSubjectTree(){
        return Result.success(treeService.buildTree());
    }
}
