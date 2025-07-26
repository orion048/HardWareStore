@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventProducer producer;

    public EventController(EventProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/{topic}")
    public ResponseEntity<Void> publish(@PathVariable String topic, @RequestBody String message) {
        producer.sendEvent(topic, message);
        return ResponseEntity.accepted().build();
    }
}