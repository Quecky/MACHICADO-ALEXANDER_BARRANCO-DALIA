package service;

import model.Odontologo;
import persistencia.dao.IDao;

import java.util.List;

public class EmpleadoService {

    private IDao<Odontologo> empleadoIDAO;

    public EmpleadoService(IDao<Odontologo> empleadoIDAO) {
        this.empleadoIDAO = empleadoIDAO;
    }

    public Odontologo registrarEmpleado(Odontologo odontologo) {
        return empleadoIDAO.crear(odontologo);
    }

    public Odontologo buscarPorId(Integer id) {
        return empleadoIDAO.buscarPorId(id);
    }

    public List<Odontologo> buscarTodos() {
        return empleadoIDAO.buscarTodos();
    }


    public void borrarPorId(Integer id) {
        empleadoIDAO.borrarPorId(id);
    }

    public Odontologo actualizar(Odontologo entidad) {
    return empleadoIDAO.actualizar(entidad);
    }

}