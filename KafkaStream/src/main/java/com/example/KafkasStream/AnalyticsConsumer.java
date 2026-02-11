import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsConsumer {

    @KafkaListener(
            topics = "frontend-events",
            groupId = "analytics-group"
    )
    public void consume(String message) {
        System.out.println("Event received: " + message);
    }
}
