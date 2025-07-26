import com.example.demo.saga.SagaCoordinator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SagaController {

    private final SagaCoordinator sagaCoordinator;

    @Autowired
    public SagaController(SagaCoordinator sagaCoordinator) {
        this.sagaCoordinator = sagaCoordinator;
    }

    @GetMapping("/api/saga/start")
    public ResponseEntity<Void> startSaga() {
        sagaCoordinator.coordinateSaga();
        return ResponseEntity.ok().build();
    }
}