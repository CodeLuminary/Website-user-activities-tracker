import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EventWebSocketHandler extends TextWebSocketHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public EventWebSocketHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        FrontendEvent event =
                mapper.readValue(message.getPayload(), FrontendEvent.class);

        if (event.type == null || event.ts == 0) {
            return;
        }

        kafkaTemplate.send(
                "frontend-events",
                event.type,
                mapper.writeValueAsString(event)
        );
    }
}
