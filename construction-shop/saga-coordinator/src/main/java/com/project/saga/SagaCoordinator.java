import com.example.demo.service.SagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SagaCoordinator {

    private final RestTemplate restTemplate;
    private final String sagaServiceUrl;
    private final SagaService sagaService;

    @Autowired
    public SagaCoordinator(RestTemplate restTemplate, String sagaServiceUrl, SagaService sagaService) {
        this.restTemplate = restTemplate;
        this.sagaServiceUrl = sagaServiceUrl;
        this.sagaService = sagaService;
    }

    public void coordinateSaga() {
        // Логика координации саги
        String response = restTemplate.getForObject(sagaServiceUrl, String.class);
        // Обработка ответа
        sagaService.executeSaga(response);
    }
}