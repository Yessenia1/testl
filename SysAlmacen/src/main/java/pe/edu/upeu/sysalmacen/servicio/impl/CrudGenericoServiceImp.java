package pe.edu.upeu.sysalmacen.servicio.impl;

import pe.edu.upeu.sysalmacen.excepciones.ModelNotFoundException;
import pe.edu.upeu.sysalmacen.modelo.CustomResponse;
import java.time.LocalDateTime;
import java.util.List;

public class CrudGenericoServiceImp<T, I> {

    // Definir la constante
    private static final String ID_NOT_FOUND_MESSAGE = "ID NOT FOUND: ";

    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(I id, T t) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException(
            ID_NOT_FOUND_MESSAGE + id));
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(I id) {
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException(
            ID_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public CustomResponse delete(I id) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException(
            ID_NOT_FOUND_MESSAGE + id));
        getRepo().deleteById(id);
        
        CustomResponse cer = new CustomResponse();
        cer.setStatusCode(200);
        cer.setDatetime(LocalDateTime.now());
        cer.setMessage("true");
        return cer;
    }

    // Método ficticio para simular el repositorio
    private CrudGenericoRepository<T, I> getRepo() {
        // Aquí debería devolver una instancia real del repositorio
        return null;
    }
}
