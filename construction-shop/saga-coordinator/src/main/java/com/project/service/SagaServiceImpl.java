import org.springframework.stereotype.Service;

@Service
public class SagaServiceImpl implements SagaService {

    @Override
    public void executeSaga(String data) {
        // Логика выполнения саги
        System.out.println("Executing saga with data: " + data);
        // Дополнительные действия
    }
}