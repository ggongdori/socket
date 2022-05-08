package com.example.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //endpoint를 /stomp로 하고, allowedOrigins를 "*"로 하면 페이지에서
    //Get /info 404 Error가 발생한다. 그래서 아래와 같이 2개의 계층으로 분리하고
    //origins를 개발 도메인으로 변경하니 잘 동작하였다.
    //이유는 왜 그런지 아직 찾지 못함
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();
    }

    /*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setPathMatcher(new AntPathMatcher(".")); // url을 chat/room/3 -> chat.room.3으로 참조하기 위한 설정
        registry.setApplicationDestinationPrefixes("/pub");

        //registry.enableSimpleBroker("/sub");
        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue");
    }

    //    private final WebSocketHandler webSocketHandler;
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler, "ws/chat")
//                //*로 전체를 허용하는 것보다 일일이 하나씩 지정해주는 것이 좋음
//                .setAllowedOrigins("http://*:8080", "http://*.*.*.*:8080")
//                .withSockJS()
//                .setClientLibraryUrl("http://localhost:8080/myapp/js/sock-client.js");
//        //.setClientLibarayUrl은 그냥 sockjs CDN 주소를 입력해도 무관하다.
//        //https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js
//
//                /*
//                Spring Boot에서 CORS 설정 시, .allowCredentials(true)와
//                .allowedOrigins("*")는 동시 설정을 못하도록 업데이트 되었다고 한다.
//                모든 주소를 허용하는 대신 특정 패턴만 허용하는 것으로 적용해야한다고 변동됨.
//
//                .allowedOrigins("*") 대신 .allowedOriginPatterns("*")를 사용하면 에러는 해결이
//                된다고 한다.
//
//                나는 이처럼 하지 않고, http://localhost:8080 또는, IP 주소로 접속하기 때문에
//                위에 설정처럼 하였다.
//                */
//    }

}
