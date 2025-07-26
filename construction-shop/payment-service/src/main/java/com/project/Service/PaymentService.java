@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment createPayment(Payment payment) {
        payment.setStatus("PENDING");
        payment.setPaidAt(LocalDateTime.now());
        return repository.save(payment);
    }

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Optional<Payment> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Payment updateStatus(Long id, String newStatus) {
        Payment payment = repository.findById(id).orElseThrow();
        payment.setStatus(newStatus);
        return repository.save(payment);
    }
}