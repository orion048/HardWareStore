@Service
public class DeliveryService {

    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public Delivery createDelivery(Delivery delivery) {
        delivery.setStatus("PENDING");
        delivery.setDeliveryDate(LocalDateTime.now());
        return repository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return repository.findAll();
    }

    public Optional<Delivery> getById(Long id) {
        return repository.findById(id);
    }

    public void deleteDelivery(Long id) {
        repository.deleteById(id);
    }

    public Delivery updateStatus(Long id, String newStatus) {
        Delivery delivery = repository.findById(id).orElseThrow();
        delivery.setStatus(newStatus);
        return repository.save(delivery);
    }
}