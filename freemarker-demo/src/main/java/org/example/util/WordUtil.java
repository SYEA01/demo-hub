package org.example.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.*;
import java.util.Map;

/**
 * @author: Taoao_
 * @create: 2025-08-20 10:17
 * @desc:
 */
public class WordUtil {
    //配置信息
    private static Configuration configuration = null;
    //模板文件的位置
    private static String tempPath;

    public WordUtil() {
        if(configuration == null){
            configuration = new Configuration(Configuration.VERSION_2_3_23);
            configuration.setDefaultEncoding("UTF-8");
        }
        if(tempPath == null ||tempPath.length()==0){
            //装载模板文件目录
            String filePath = this.getClass().getClassLoader().getResource("/").getPath();
            int end = filePath.lastIndexOf("WEB-INF/");
            String basePath = filePath.substring(0,end);
            tempPath = basePath + "WEB-INF/page/temp/";
        }
    }

    public byte[] getByteWord(Map<String, Object> dataMap, String tempName) throws IOException {
        byte[] content = null;
        File file = null;
        InputStream in = null;
        try {
            //模板文件路径
            File readFile = new File(tempPath);
            configuration.setDirectoryForTemplateLoading(readFile);
            //加载模板文件
            Template template = configuration.getTemplate(tempName);
            //创建临时文件，用于保存要导出的数据
            file = new File("report.doc");
            //这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            //填充数据
            template.process(dataMap, w);
            //将临时文件数据读入文件流
            in = new FileInputStream(file);
            content=new byte[(int)file.length()];
            in.read(content);
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if(in != null) in.close();
            //删除临时文件
            if(file != null) file.delete();
        }
        return content;
    }

    public static HttpHeaders setResponseHeaderWord(String fileName){
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("gbk"), "iso8859-1")+".doc");
            headers.setContentType(MediaType.valueOf("application/msword"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return headers;
    }

}
