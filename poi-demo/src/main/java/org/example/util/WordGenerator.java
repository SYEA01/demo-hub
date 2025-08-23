package org.example.util;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Taoao_
 * @create: 2025-08-18 09:13
 * @desc:
 */
public class WordGenerator {


    public static void htmlToWord(String html) throws Exception {
        // 创建Word文档
        XWPFDocument document = new XWPFDocument();

        // 尝试获取样式对象，如果不存在则创建
        document.createStyles();

        // 1. 添加封面（必须最先添加）
        addCoverPage(document);

        // 2. 插入目录字段
        insertTOC(document);

        insertTable(document);

        // 3. 解析HTML并添加实际内容
        Document parsedHtml = Jsoup.parse(html);
        Elements elements = parsedHtml.body().children();
        for (Element element : elements) {
            processElementForContent(document, element);
        }

        // 4. 创建页脚并添加页码
        createPageFooter(document);


        // 保存文档
        String wordName = "document_" + System.currentTimeMillis() + ".docx";

        try (FileOutputStream out = new FileOutputStream(wordName)) {
            document.write(out);
        }
        document.close();


        Map<String,String> map = new HashMap<>();
        map.put("title","陶鹏程");
        map.put("title2","桃桃淘");
        XWPFTemplate.compile(wordName).render(map).writeAndClose(new FileOutputStream(wordName));

//        String pdfName =
//        .replace(".docx", ".pdf");
//        wordToPdf(wordName, pdfName);
    }

    private static void insertTable(XWPFDocument document) {

        // 从资源加载封面模板
        ClassPathResource resource = new ClassPathResource("template/template-table.docx");
        try (InputStream is = resource.getInputStream()) {
            XWPFDocument coverDoc = new XWPFDocument(OPCPackage.open(is));

            // 模版文档
            XWPFTable sourceTable = coverDoc.getTables().get(0);
            // 行数和猎数
            int rowCount = sourceTable.getNumberOfRows();
            int colCount = sourceTable.getRow(0).getTableCells().size();
            XWPFTable table = document.createTable(rowCount, colCount);
            for (int i = 0; i < rowCount; i++) {
                XWPFTableRow row = sourceTable.getRow(i);
                XWPFTableRow newRow = table.getRow(i);
                for (int j = 0; j < row.getTableCells().size(); j++) {
                    XWPFTableCell cell = row.getCell(j);
                    XWPFTableCell newCell = newRow.getCell(j);
                    newCell.removeParagraph(0);
                    for (XWPFParagraph para : cell.getParagraphs()) {
                        XWPFParagraph newPara = newCell.addParagraph();
//                        XWPFParagraph newPara = newCell.getParagraph();
                        while (newPara.getRuns().size() > 0) {
                            newPara.removeRun(0);
                        }
                        // 设置文本，以及样式
                        for (XWPFRun run : para.getRuns()) {
                            XWPFRun newRun = newPara.createRun();
                            newRun.setText(run.getText(0));
                            newRun.setFontFamily(run.getFontFamily());
                            newRun.setBold(run.isBold());
                        }
                        // 设置对齐方式
                        newPara.setAlignment(para.getAlignment());
                    }
                    // 设置单元格的宽度
                    newCell.setWidth(String.valueOf(cell.getWidth()));
                }
            }

            // 添加分页符
            document.createParagraph().setPageBreak(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }


    private static boolean wordToPdf(String wordPath, String pdfPath) {
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


//    public static void wordToPdf(String wordPath, String pdfPath) throws Exception {
//        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(wordPath));
//             PDDocument pdfDocument = new PDDocument()) {
//
//
//            // 创建一个页面
//            PDPage page = new PDPage();
//            pdfDocument.addPage(page);
//
//            // 开始写入内容到PDF页面
//            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);
//            contentStream.beginText();
////            contentStream.setFont(PDType1Font.HELVETICA, 12);
//            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
//            contentStream.setLeading(14.5f); // 设置行间距
//            contentStream.newLineAtOffset(25, 700); // 设置文本起始位置
//
//            // 读取Word文档内容并写入到PDF中（这里简化处理，实际应用中可能需要更复杂的处理）
//            for (XWPFParagraph p : doc.getParagraphs()) {
//                String text = p.getText();
//                contentStream.showText(text);
//                contentStream.newLine(); // 换行
//            }
//            contentStream.endText();
//            contentStream.close();
//
//            // 保存PDF文件
//            pdfDocument.save(pdfPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void wordToPdf(String wordPath, String pdfPath) throws Exception {
//        try (InputStream in = new FileInputStream(wordPath);
//                OutputStream out = new FileOutputStream(pdfPath)) {
//
//            // 1. 加载Word文档
//            XWPFDocument document = new XWPFDocument(in);
//
//            // 2. 配置PDF选项
//            PdfOptions options = PdfOptions.create();
//
//            // 3. 执行转换
//            PdfConverter.getInstance().convert(document, out, options);
//        }
//
//    }

    /**
     * 创建页脚并添加页码
     *
     * @param doc
     */
    private static void createPageFooter(XWPFDocument doc) {
        // 获取文档的节设置
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc, sectPr);

        // 创建默认页脚（适用于所有页）
        XWPFFooter footer = policy.createFooter(STHdrFtr.DEFAULT);

        // 创建页脚段落（居中对齐）
        XWPFParagraph footerPara = footer.createParagraph();
        footerPara.setAlignment(ParagraphAlignment.CENTER);

        // 添加页码文本（"第"）
        XWPFRun run = footerPara.createRun();
        run.setText("第 ");

        // 插入页码字段（关键步骤）
        CTSimpleField pageField = footerPara.getCTP().addNewFldSimple();
        pageField.setInstr("PAGE \\* MERGEFORMAT"); // 页码字段指令
        pageField.setDirty(STOnOff.Factory.newValue("1")); // 设置需要更新字段
//        pageField.setDirty(STOnOff.TRUE); // 设置需要更新字段

        // 添加页码后文本
        run = footerPara.createRun();
        run.setText(" 页");

        // 可选：设置页码起始值（默认从1开始）
        CTPageNumber pageNum = sectPr.addNewPgNumType();
        pageNum.setStart(BigInteger.valueOf(1)); // 设置起始页码
    }

    /**
     * 插入目录字段
     */
    private static void insertTOC(XWPFDocument document) {
        // 添加"目录"标题
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER); // 居中
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText("目 录");
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setFontFamily("宋体");

        // 创建目录字段段落
        XWPFParagraph tocPara = document.createParagraph();
        XWPFRun tocRun = tocPara.createRun();
        tocRun.setText(""); // 初始空白文本

        // 创建目录字段
        CTSimpleField ctField = tocPara.getCTP().addNewFldSimple();
        ctField.setInstr("TOC \\o \"1-3\" \\h \\z \\u"); // 目录指令
        ctField.setDirty(STOnOff.Factory.newValue("1")); // 设置为需要更新
//        ctField.setDirty(STOnOff.TRUE); // 设置为需要更新

        // 添加字段占位文本
        ctField.addNewR().addNewT().setStringValue("生成文档后，请选中目录，然后按F9键更新目录");
        // 添加分页符
        document.createParagraph().setPageBreak(true);
    }

    /**
     * 处理元素用于内容显示
     *
     * @param document
     * @param element
     */
    private static void processElementForContent(XWPFDocument document, Element element) {
        String tagName = element.tagName().toLowerCase();
        String text = element.text();

        if (text.trim().isEmpty()) return;

        switch (tagName) {
            case "h1":
                addHeading(document, text, 1);
                break;
            case "h2":
                addHeading(document, text, 2);
                break;
            case "h3":
                addHeading(document, text, 3);
                break;
            case "p":
                addParagraph(document, text);
                break;
            default:
                addParagraph(document, text);
        }
    }


    /**
     * 添加标题
     * 如果需要目录的话，要给标题添加书签
     * 修改后的 addHeading 方法 - 使用大纲级别
     */
    private static void addHeading(XWPFDocument document, String text, int level) {
        XWPFParagraph para = document.createParagraph();

        // 使用样式并设置大纲级别
        para.setStyle("Heading" + level);
        para.setPageBreak(false); // 确保不添加分页符

        // 设置大纲级别
        CTPPr ppr = para.getCTP().addNewPPr();
        ppr.addNewOutlineLvl().setVal(BigInteger.valueOf(level - 1));

        // 创建书签
        String bookmarkName = "_Toc" + System.currentTimeMillis() + "_" + para.hashCode();
        CTBookmark ctBookmark = para.getCTP().addNewBookmarkStart();
        ctBookmark.setName(bookmarkName);
        ctBookmark.setId(BigInteger.valueOf(1));

        // 添加标题文本
        XWPFRun run = para.createRun();
        run.setText(text);
        run.setBold(true);
        run.setFontSize(level == 1 ? 20 : level == 2 ? 16 : 14);

        // 结束书签
        CTMarkupRange ctMarkupRange = para.getCTP().addNewBookmarkEnd();
        ctMarkupRange.setId(BigInteger.valueOf(1));
    }

    /**
     * 添加正文
     *
     * @param document
     * @param text
     */
    private static void addParagraph(XWPFDocument document, String text) {
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setText(text);
        run.setFontSize(12);
        para.setSpacingAfter(100); // 段落间距
    }

    /**
     * 添加封面
     *
     * @param document
     * @throws IOException
     * @throws InvalidFormatException
     */
    private static void addCoverPage(XWPFDocument document) throws IOException, InvalidFormatException {
        // 从资源加载封面模板
        ClassPathResource resource = new ClassPathResource("template/template.docx");
        try (InputStream is = resource.getInputStream()) {
            XWPFDocument coverDoc = new XWPFDocument(OPCPackage.open(is));

            // 将封面内容复制到主文档
            for (XWPFParagraph p : coverDoc.getParagraphs()) {
                XWPFParagraph newP = document.createParagraph();
                newP.getCTP().set(p.getCTP());
            }

            // 添加分页符
            document.createParagraph().setPageBreak(true);
        }
    }
}