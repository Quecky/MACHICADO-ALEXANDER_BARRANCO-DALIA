package persistencia.impl;

import model.Odontologo;
import org.apache.log4j.Logger;
import persistencia.dao.IDao;

import java.util.ArrayList;
import java.util.List;

public class OdontologoEnMemoria implements IDao<Odontologo> {
    private Logger LOGGER = Logger.getLogger(OdontologoEnMemoria.class);
    private List<Odontologo> odontologos = new ArrayList<>();

    @Override
    public Odontologo crear(Odontologo entidad) {
        Integer id = odontologos.size()+1;
        odontologos.add(entidad);
        entidad.setId(id);
        LOGGER.info("Odontologo agregado "+entidad);
        return entidad;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        for(Odontologo odontologo:odontologos)
        {

          LOGGER.info("Odontologo "+odontologo);

        }
        return odontologos;
    }
}
