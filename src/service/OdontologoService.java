package service;

import model.Odontologo;
import persistencia.dao.IDao;

import java.util.List;

public class OdontologoService {

    private IDao<Odontologo> odontologoIDAO;

    public OdontologoService(IDao<Odontologo> odontologoIDAO) {
        this.odontologoIDAO = odontologoIDAO;
    }

    public Odontologo registrarOdontologo(Odontologo odontologo) {
        return odontologoIDAO.crear(odontologo);
    }


    public List<Odontologo> buscarTodos() {
        return odontologoIDAO.buscarTodos();
    }



}