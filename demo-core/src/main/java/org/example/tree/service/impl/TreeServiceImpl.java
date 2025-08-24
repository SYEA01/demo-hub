package org.example.tree.service.impl;

import org.example.tree.entity.vo.SubjectFolderVO;
import org.example.tree.entity.vo.SubjectVO;
import org.example.tree.entity.vo.TreeNode;
import org.example.tree.service.SubjectFolderService;
import org.example.tree.service.SubjectService;
import org.example.tree.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Rambo
 * @create: 2025-08-24 20:59
 * @desc:
 */
@Service
public class TreeServiceImpl implements TreeService {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectFolderService folderService;

    @Override
    public List<TreeNode> buildTree() {
        // 首先查询出所有的课题和所有的文件夹
        List<SubjectVO> allSubjects = subjectService.getAllSubjects();
        List<SubjectFolderVO> allFolders = folderService.getAllFolders();
        return build(allSubjects, allFolders);
    }

    private List<TreeNode> build(List<SubjectVO> subjects, List<SubjectFolderVO> folders) {
        // 创建映射表，用于快速查找节点
        Map<Long, TreeNode> folderNodeMap = new HashMap<>();
        List<TreeNode> rootNodes = new ArrayList<>();

        // 1. 首先处理所有文件夹，创建文件夹节点并建立映射
        for (SubjectFolderVO folder : folders) {
            TreeNode folderNode = folderToTreeNode(folder);
            folderNodeMap.put(folder.getId(), folderNode);

            // 如果是根文件夹（没有父文件夹），直接添加到根节点列表
            if (folder.getParentId() == null) {
                rootNodes.add(folderNode);
            }
        }

        // 2. 建立文件夹之间的层级关系
        for (SubjectFolderVO folder : folders) {
            if (folder.getParentId() != null) {
                TreeNode folderNode = folderNodeMap.get(folder.getId());
                TreeNode parentNode = folderNodeMap.get(folder.getParentId());

                if (parentNode != null) {
                    // 找到父节点，将当前文件夹添加到父节点的children中
                    parentNode.getChildren().add(folderNode);
                } else {
                    // 父节点不存在，将其作为根节点
                    rootNodes.add(folderNode);
                }
            }
        }

        // 3. 处理所有课题，将它们添加到对应的文件夹中
        for (SubjectVO subject : subjects) {
            TreeNode subjectNode = subjectToTreeNode(subject);

            if (subject.getFolderId() != null) {
                // 课题有所属文件夹，找到对应的文件夹节点
                TreeNode parentFolderNode = folderNodeMap.get(subject.getFolderId());

                if (parentFolderNode != null) {
                    // 找到父文件夹，将课题添加到文件夹的children中
                    parentFolderNode.getChildren().add(subjectNode);
                } else {
                    // 父文件夹不存在，将课题作为根节点
                    rootNodes.add(subjectNode);
                }
            } else {
                // 课题没有所属文件夹，直接作为根节点
                rootNodes.add(subjectNode);
            }
        }

        return rootNodes;
    }

    private TreeNode folderToTreeNode(SubjectFolderVO folder) {
        TreeNode treeNode = new TreeNode();
        treeNode.setEntityId(folder.getId());
        treeNode.setEntityName(folder.getName());
        treeNode.setType(TreeNode.NodeType.FOLDER);
        treeNode.setParentId(folder.getParentId());
//        treeNode.setIsLeaf(false); // 文件夹不是叶子节点
        treeNode.setData(folder);
        return treeNode;
    }

    private TreeNode subjectToTreeNode(SubjectVO subject) {
        TreeNode treeNode = new TreeNode();
        treeNode.setEntityId(subject.getId());
        treeNode.setEntityName(subject.getTitle());
        treeNode.setType(TreeNode.NodeType.SUBJECT);
        treeNode.setParentId(subject.getFolderId());
//        treeNode.setIsLeaf(true); // 课题是叶子节点
        treeNode.setData(subject);
        return treeNode;
    }
}
