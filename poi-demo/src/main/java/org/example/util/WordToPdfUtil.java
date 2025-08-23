package org.example.util;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * word转pdf工具类
 * <p>
 * 注意：在linux服务器上部署使用时，需要先安装libreoffice和相关字体
 *
 * @author xption
 */
public class WordToPdfUtil {


//    /**
//     * word转pdf
//     * 支持docx和doc格式
//     *
//     * @param inputStream 输入流
//     * @param fileName    文件名
//     * @param convertTmpDir    转换文件临时存放目录(仅限linux系统)
//     * @return
//     */
//    public static byte[] wordConvertPdf(InputStream inputStream, String fileName, String convertTmpDir) {
//        // 获取当前操作系统的名称
//        String osName = System.getProperty("os.name").toLowerCase();
//        if (osName.contains("win")) {
//            return wordConvertPdfWindows(inputStream, fileName);
//        } else {
//            return docxConvertPdfLinux(inputStream, fileName, convertTmpDir);
//        }
//    }

    /**
     * word转pdf
     * 支持docx和doc格式
     * Windows系统版
     *
     * @param inputStream 输入流
     * @param fileName    文件名
     * @return
     */
    public static byte[] wordConvertPdfWindows(InputStream inputStream, String fileName) {
        if (fileName.endsWith(".docx")) {
            return docxConvertPdfWindows(inputStream);
        } else {
            throw new RuntimeException("");
        }
    }

    /**
     * word转pdf
     * 仅支持docx格式
     * Windows系统版
     *
     * @param inputStream 输入流
     * @return
     */
    private static byte[] docxConvertPdfWindows(InputStream inputStream) {
        // 读取word模板
        try (XWPFDocument xwpfDocument = new XWPFDocument(inputStream)) {
            // 将修改后的 Word 文档写入临时字节数组
            ByteArrayOutputStream docxOut = new ByteArrayOutputStream();
            xwpfDocument.write(docxOut);

            // 创建输入流用于转换
            try (ByteArrayInputStream in = new ByteArrayInputStream(docxOut.toByteArray());
                 ByteArrayOutputStream pdfOut = new ByteArrayOutputStream()) {
                IConverter converter = LocalConverter.builder().build();
                converter.convert(in).as(DocumentType.DOCX).to(pdfOut).as(DocumentType.PDF).execute();
                // 返回转换后的 PDF 字节
                return pdfOut.toByteArray();
            }
        } catch (Exception e) {
            return null;
        }
    }


    private static boolean docxConvertPdfWindows(String wordPath, String pdfPath) {
        try (XWPFDocument xwpfDocument = new XWPFDocument(Files.newInputStream(Paths.get(wordPath)))) {
            // 将 Word 文档写入字节数组
            ByteArrayOutputStream docxOut = new ByteArrayOutputStream();
            xwpfDocument.write(docxOut);

            // 创建输入流用于 PDF 转换
            try (ByteArrayInputStream in = new ByteArrayInputStream(docxOut.toByteArray());
                 ByteArrayOutputStream pdfOut = new ByteArrayOutputStream()) {

                // 初始化转换器并执行转换
                IConverter converter = LocalConverter.builder().build();
                converter.convert(in).as(DocumentType.DOCX).to(pdfOut).as(DocumentType.PDF).execute();

                // 确保输出目录存在
                File file = new File(pdfPath);
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                    System.err.println("无法创建输出目录: " + parentDir.getAbsolutePath());
                    return false;
                }

                // 将 PDF 内容写入文件
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(pdfOut.toByteArray());
                }

                return true; // 转换并保存成功
            }
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常信息
            return false; // 操作失败
        }
    }

//    /**
//     * word转pdf
//     * 仅支持doc格式
//     * Windows系统版
//     *
//     * @param inputStream 输入流
//     * @return
//     */
//    private static byte[] docConvertPdf(InputStream inputStream) {
//        try (ByteArrayOutputStream pdfOut = new ByteArrayOutputStream()) {
//            IConverter converter = LocalConverter.builder().build();
//            converter.convert(inputStream).as(DocumentType.DOC).to(pdfOut).as(DocumentType.PDF).execute();
//            return pdfOut.toByteArray();
//        } catch (Exception e) {
//            logger.error("word(doc格式)转pdf失败!", e);
//            return null;
//        }
//    }
//
//
//    /**
//     * word转pdf
//     * 仅支持docx格式
//     * linux系统版
//     *
//     * @param inputStream   输入流
//     * @param fileName      文件名称
//     * @param convertTmpDir 转换文件临时存放目录
//     * @return
//     */
//    public static byte[] docxConvertPdfLinux(InputStream inputStream, String fileName, String convertTmpDir) {
//        // 确保基础目录存在
//        File baseFolder = new File(convertTmpDir);
//        if (!baseFolder.exists()) {
//            baseFolder.mkdirs();
//        }
//        // 生成唯一的文件名避免冲突
//        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
//        File inputFile = new File(baseFolder, uniqueFileName);
//        File outputFile;
//        if (StringUtils.endsWith(fileName, ".docx")) {
//            outputFile = new File(baseFolder, uniqueFileName.replace(".docx", ".pdf"));
//        } else if (StringUtils.endsWith(fileName, ".doc")) {
//            outputFile = new File(baseFolder, uniqueFileName.replace(".doc", ".pdf"));
//        } else {
//            throw new BusinessException(ResponseCodeEnum.ILLEGAL_FILENAME);
//        }
//
//        // 将输入流写入文件
//        try (FileOutputStream fileOutputStream = new FileOutputStream(inputFile)) {
//            byte[] buffer = new byte[8192];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                fileOutputStream.write(buffer, 0, bytesRead);
//            }
//        } catch (IOException e) {
//            logger.error("写入文件失败!", e);
//            return null;
//        }
//        // 构建LibreOffice的命令行工具命令
//        String command = "libreoffice --invisible --convert-to pdf --outdir " + convertTmpDir + " " + inputFile;
//        // 执行转换命令
//        try {
//            executeLinuxCmd(command);
//        } catch (Exception e) {
//            logger.error("linuxWordToPdf linux环境word转换为pdf时出现异常：", e);
//        }
//
//        // 读取转换后的PDF文件
//        try (FileInputStream pdfInputStream = new FileInputStream(outputFile);
//             ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
//            byte[] buffer = new byte[8192];
//            int bytesRead;
//            while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
//                pdfOutputStream.write(buffer, 0, bytesRead);
//            }
//            // 清理临时文件
//            deleteFile(inputFile);
//            deleteFile(outputFile);
//
//            return pdfOutputStream.toByteArray();
//        } catch (Exception e) {
//            logger.error("linuxWordToPdf linux环境word转换为pdf时出现异常：", e);
//        }
//        return null;
//    }
//
//
//    /**
//     * 执行命令行
//     *
//     * @param cmd 命令行
//     */
//    private static void executeLinuxCmd(String cmd) {
//        try {
//            Process process = Runtime.getRuntime().exec(cmd);
//            process.waitFor();
//        } catch (InterruptedException e) {
//            logger.error("executeLinuxCmd 执行Linux命令异常：", e);
//            Thread.currentThread().interrupt();
//        } catch (Exception e) {
//            logger.error("获取系统命令执行环境异常", e);
//        }
//    }
//
//
//    /**
//     * 安全删除文件
//     *
//     * @param file 要删除的文件
//     */
//    private static void deleteFile(File file) {
//        if (file != null && file.exists()) {
//            try {
//                file.delete();
//            } catch (Exception e) {
//                logger.warn("删除临时文件失败: {}", file.getAbsolutePath(), e);
//            }
//        }
//    }

}