package libs.sse;

import libs.sse.CustomSseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomSseEmitterList {
    private static List<CustomSseEmitter> emitterList = new CopyOnWriteArrayList<>();

    static AtomicInteger counter;
    public CustomSseEmitterList (){
        counter = new AtomicInteger(0);
    }

    public CustomSseEmitter add (CustomSseEmitter emitter){
        emitterList.add(emitter);
        emitter.onCompletion(()->{
            emitterList.remove(emitter); //Emitter 객체가 다시 생성 되기 때문에 자기 자신 지우기.
        });
        emitter.onTimeout(()->{
            emitter.complete(); //타임아웃 발생 시, 브라우저에서 재연결 요청 & 새로운 Emitter 객체 생성.
        });
        return emitter;
    }

    public void send (int port, String data){
        Optional<CustomSseEmitter> matchingEmitter = emitterList.stream()
                        .filter (emitter -> port == emitter.getPort())
                                .findFirst();

        matchingEmitter.ifPresent(emitter -> {
            try {
                emitter.send(CustomSseEmitter.event().data(Map.of("port", port, "data", data)));
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
                throw new RuntimeException(e);

            }
        });
    }
}
