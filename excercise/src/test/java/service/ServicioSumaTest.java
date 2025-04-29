package service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.CompletableFuture;

// (sin public)
class ServicioSumaTest {

    @Test
    void testSumarConRetraso() {
        ServicioSuma servicioSuma = new ServicioSuma();

        CompletableFuture<Integer> resultadoFuturo = servicioSuma.sumarConRetraso(5, 7, 2);

        Integer resultado = resultadoFuturo.join();

        assertEquals(12, resultado);
    }
}
