package org.example.service.impl;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.example.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Taoao_
 * @create: 2025-08-26 16:13
 * @desc:
 */
@Slf4j
@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ThreadPoolExecutor executor;

    private Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    @Override
    public SseEmitter demo() {
        SseEmitter emitter = new SseEmitter(60000L);
        String taskId = UUID.randomUUID().toString();
        emitterMap.put(taskId, emitter);

        CompletableFuture.runAsync(() -> {
            // TODO 生成文件

            // 发送进度消息
            SseEmitter emitter1 = emitterMap.get(taskId);
            try {

                for (int i = 0; i < 100; i++) {
                    // 暂停毫秒
                    try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }

                    String name = Thread.currentThread().getName() + "progress";
                    log.info("发送进度消息:{}", i);
                    emitter1.send(SseEmitter.event().name(name).data(i).id(taskId));
                }
                log.info("发送完成");
                emitter1.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, executor);
        return emitter;
    }
}
