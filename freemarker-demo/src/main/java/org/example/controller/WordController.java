package org.example.controller;

import freemarker.template.TemplateException;
import org.example.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Taoao_
 * @create: 2025-08-20 10:01
 * @desc:
 */
@RestController
@RequestMapping("/demo")
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping("/generate")
    public void generateWordDocument() {
        try {
            // 准备模板数据
            Map<String, Object> data = new HashMap<>();
            data.put("title", "Spring Boot与FreeMarker生成Word文档示例");
            data.put("title2", "Spring Boot与FreeMarker生成Word文档示例");
            data.put("currentDate", java.time.LocalDate.now().toString());


            // 生成文档
            byte[] document = wordService.generateWordDocument(data);

            // 保存文档
            String wordName = "document_" + System.currentTimeMillis() + ".docx";

            try (FileOutputStream fos = new FileOutputStream(wordName)) {
                fos.write(document);
                System.out.println("Word文件已成功保存到: " + wordName);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("保存文件时发生错误: " + e.getMessage());
            }
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
