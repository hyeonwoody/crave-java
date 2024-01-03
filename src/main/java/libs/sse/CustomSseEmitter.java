package libs.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class CustomSseEmitter extends SseEmitter {
    private int port;
    private Long port1;
    public CustomSseEmitter (int port){
        super();
        this.port = port;
    }

    public CustomSseEmitter (Long port){
        super();
        this.port1 = port;
    }


    int getPort(){
        return this.port;
    }
}
