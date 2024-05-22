package persistencia.impl;

import db.H2Connection;
import model.Odontologo;
import org.apache.log4j.Logger;
import persistencia.dao.IDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static String SQL_INSERT = "INSERT INTO ODONTOLOGOS VALUES(DEFAULT,?,?,?)";
    private static String SQL_SELECT_ALL="SELECT * FROM ODONTOLOGOS";
    public static Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);
    @Override
    public Odontologo crear(Odontologo entidad) {
        Odontologo odontologoAretornar =null;
        Connection connection = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entidad.getNombre());
            preparedStatement.setString(2,entidad.getApellido());
            preparedStatement.setString(3,entidad.getNumeroMatricula());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next())
            {
                Integer id = resultSet.getInt(1);
                odontologoAretornar = new Odontologo(id,entidad.getNombre(),entidad.getApellido(),entidad.getNumeroMatricula());
            }
            LOGGER.info("Odontologo persistido "+ odontologoAretornar);
            //el commit es para hacer modificaciones en la BD
            //no se necesita para listar
            connection.commit();

            connection.setAutoCommit(true);

        }catch(Exception e)
        {
            if(connection!=null)
            {
                try{
                    connection.rollback();
                }catch(SQLException ex)
                {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        finally
        {

            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        return odontologoAretornar;
    }

    @Override
    public List<Odontologo> buscarTodos() {


        List<Odontologo> odontologos =new ArrayList<>();

        Connection connection = null;
        try
        {
            connection = H2Connection.getConnection();
            Statement statment = connection.createStatement();
            ResultSet resultSet = statment.executeQuery(SQL_SELECT_ALL);
            while(resultSet.next())
            {
                Integer IdDevuelto = resultSet.getInt(1);
                String nombre=resultSet.getString(2);
                String apellido=resultSet.getString(3);
                String matricula=resultSet.getString(4);



                Odontologo odontologo = new Odontologo(IdDevuelto,nombre,apellido,matricula);
                LOGGER.info("Odontologo listado "+ odontologo);
                odontologos.add(odontologo);

            }

        }catch(Exception e)
        {

            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        return odontologos;
    }

}
