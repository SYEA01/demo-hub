package org.example.service;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author: Taoao_
 * @create: 2025-08-20 10:02
 * @desc:
 */
@Service
public class WordService {
    @Autowired
    private Configuration freemarkerConfig;

    public byte[] generateWordDocument(Map<String, Object> data) throws IOException, TemplateException {
        // 获取模板
        Template template = freemarkerConfig.getTemplate("template.ftl");

        // 填充数据
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

        // 返回字节数组
        return content.getBytes("UTF-8");
    }
}
