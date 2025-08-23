package org.example.controller;

import org.example.entity.HtmlToWordRequest;
import org.example.util.WordGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author: Taoao_
 * @create: 2025-08-18 09:15
 * @desc:
 */



/**
 * @author: Rambo
 * @create: 2025-08-07 19:39
 * @desc:
 */
@RestController
@RequestMapping("/demo")
public class ConverseController {


    @PostMapping("/export")
    public ResponseEntity<String> exportToWord(@RequestBody HtmlToWordRequest request) throws Exception {
        WordGenerator.htmlToWord(request.getContent());

        return ResponseEntity.ok("保存成功");
    }


}
