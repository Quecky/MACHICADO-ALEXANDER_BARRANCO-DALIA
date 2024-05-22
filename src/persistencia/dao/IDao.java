package persistencia.dao;

import java.util.List;

public interface IDao <T>{
    T crear(T entidad);

    List<T> buscarTodos();

}
