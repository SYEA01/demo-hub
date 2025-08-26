package org.example.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Taoao_
 * @create: 2025-08-26 15:01
 * @desc:
 */
@RestController
@RequestMapping("/progress")
public class ProgressController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseEmitter() {
        SseEmitter emitter = new SseEmitter(0L);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    // 模拟任务处理延迟
                    Thread.sleep(100);

                    // 发送进度事件
                    emitter.send(SseEmitter.event().name("progress").data(i));

                }
                // 发送完成事件
                emitter.send(SseEmitter.event().name("complete").data("任务完成"));
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                emitter.completeWithError(e);
            }
        });

        executor.shutdown();
        return emitter;
    }
}
