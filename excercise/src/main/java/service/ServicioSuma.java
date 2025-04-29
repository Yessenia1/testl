package service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ServicioSuma {  // ← Notar que quité "public" si quieres seguir las buenas prácticas de Sonar

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    CompletableFuture<Integer> sumarConRetraso(int a, int b, int segundos) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        scheduler.schedule(() -> future.complete(a + b), segundos, TimeUnit.SECONDS);
        return future;
    }
}
