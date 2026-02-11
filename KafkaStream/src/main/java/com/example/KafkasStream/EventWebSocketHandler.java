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
