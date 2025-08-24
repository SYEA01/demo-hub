# 需要实现的接口
- 创建目录或文件
- 根据id获取
- 删除，如果是目录就递归删除
- 获取指定目录下的所有子资源
- 获取目录树

## 一、文件夹相关接口

### 1. 创建文件夹

- **端点**: POST /api/folders
- **请求体**:

```json
{
  "name": "文件夹名称",
  "parentId": 0 // 0表示根目录，其他数字表示父文件夹ID
}
```

- **功能**: 创建新文件夹，可以指定父文件夹

### 2. 更新文件夹

- **端点**: PUT /api/folders/{id}
- **请求体**:

```json
{
  "name": "新名称",
  "parentId": 123 // 可选的父文件夹ID更改
}
```

- **功能**: 重命名文件夹或移动文件夹到新的父文件夹

### 3. 删除文件夹

- **端点**: DELETE /api/folders/{id}
- **功能**: 删除文件夹及其所有子文件夹和课题（需要确认业务逻辑）

### 4. 获取文件夹详情

- **端点**: GET /api/folders/{id}
- **功能**: 获取单个文件夹的详细信息

### 5. 获取文件夹树

- **端点**: GET /api/folders/tree
- **查询参数**:
    - `includeSubjects` (可选): 是否包含课题
    - `userId` (可选): 用户ID过滤
- **功能**: 获取完整的文件夹树形结构

## 二、课题相关接口

### 1. 创建课题

- **端点**: POST /api/subjects
- **请求体**:

```json
{
  "title": "课题名称",
  "folderId": 123 // 所属文件夹ID，0表示根目录
}
```

- **功能**: 创建新课题并指定所属文件夹

### 2. 更新课题

- **端点**: PUT /api/subjects/{id}
- **请求体**:

```json
{
  "title": "新名称",
  "folderId": 456 // 可选的文件夹更改
}
```

- **功能**: 更新课题信息或移动课题到其他文件夹

### 3. 删除课题

- **端点**: DELETE /api/subjects/{id}
- **功能**: 删除指定课题

### 4. 获取课题详情

- **端点**: GET /api/subjects/{id}
- **功能**: 获取单个课题的详细信息

### 5. 获取文件夹下的课题列表

- **端点**: GET /api/subjects
- **查询参数**:
    - `folderId`: 文件夹ID
    - `userId`: 用户ID
- **功能**: 获取指定文件夹下的所有课题

## 三、特殊功能接口

### 1. 拖拽移动接口

- **端点**: POST /api/move
- **请求体**:

```json
{
  "type": "folder", // 或 "subject"
  "id": 123, // 要移动的项目ID
  "targetFolderId": 456 // 目标文件夹ID
}
```

- **功能**: 处理文件夹或课题的拖拽移动操作

### 2. 批量操作接口

- **端点**: POST /api/batch
- **请求体**:

```json
{
  "operation": "delete", // 或 "move"
  "type": "mixed", // "folders", "subjects" 或 "mixed"
  "folderIds": [1, 2, 3],
  "subjectIds": [4, 5, 6],
  "targetFolderId": 789 // 移动操作时使用
}
```

- **功能**: 批量删除或移动多个文件夹和课题

## 四、响应数据结构

### 文件夹树节点响应

```java
public class TreeNode {
    private Long id;
    private String name;
    private String type; // "folder" 或 "subject"
    private Long parentId;
    private List<TreeNode> children;
    // 其他属性...
}
```

### 错误响应

```java
public class ErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp;
}
```


## ✅ 二、你需要的完整接口清单

| 接口功能                          | 方法     | 路径                      | 参数                          | 说明                           |
| --------------------------------- | -------- | ------------------------- | ----------------------------- | ------------------------------ |
| 1. 获取完整树结构（含未归类课题） | `GET`    | `/tree`                   | `userId`                      | 核心接口，返回整个结构         |
| 2. 懒加载：获取某文件夹子结构     | `GET`    | `/tree/folder/{folderId}` | `folderId`, `userId`          | 用于展开节点时异步加载         |
| 3. 移动课题到文件夹               | `PUT`    | `/subject/move`           | `subjectId`, `targetFolderId` | 支持移动到具体文件夹           |
| 4. 移动课题到“无分组”             | `PUT`    | `/subject/uncategorize`   | `subjectId`                   | 设置 `folderId = null`         |
| 5. 移动文件夹（调整父级）         | `PUT`    | `/folder/move`            | `folderId`, `newParentId`     | 支持拖拽改变层级，防止成环     |
| 6. 创建文件夹                     | `POST`   | `/folder`                 | `{name, parentId, userId}`    | `parentId` 可为 null（表示根） |
| 7. 删除课题                       | `DELETE` | `/subject/{id}`           | `id`, `userId`                | 建议软删或硬删                 |
| 8. 删除文件夹（级联）             | `DELETE` | `/folder/{id}`            | `id`, `userId`                | 删除其下所有子文件夹 & 课题    |
| 9. 重命名文件夹                   | `PUT`    | `/folder/rename`          | `id`, `name`                  | 修改名称                       |
| 10. 搜索课题或文件夹              | `GET`    | `/search`                 | `keyword`, `userId`           | 全局模糊搜索                   |

