@Service
public class EventListener {

    @KafkaListener(topics = "order-events", groupId = "saga-group")
    public void listen(String message) {
        System.out.println("📩 Получено сообщение: " + message);
        // Здесь можешь вызвать обработку платежа, доставки и т.д.
    }
}