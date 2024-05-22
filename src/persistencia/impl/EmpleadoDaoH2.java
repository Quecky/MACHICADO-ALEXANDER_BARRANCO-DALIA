package persistencia.impl;

import db.H2Connection;
import model.Odontologo;
import org.apache.log4j.Logger;
import persistencia.dao.IDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDaoH2 implements IDao<Odontologo> {
    private static String SQL_INSERT = "INSERT INTO EMPLEADOS VALUES(DEFAULT,?,?,?,?)";
    private static String SQL_SELECT_ID="SELECT * FROM EMPLEADOS WHERE ID=?";
    private static String SQL_SELECT_ALL="SELECT * FROM EMPLEADOS";

    private static String SQL_DELETE="DELETE FROM EMPLEADOS WHERE ID=?";

    private static String SQL_UPDATE="UPDATE EMPLEADOS SET APELLIDO=?,NOMBRE=?,DNI=?,FECHA_NACIMIENTO=? WHERE ID=?";
    public static Logger LOGGER = Logger.getLogger(EmpleadoDaoH2.class);
    @Override
    public Odontologo crear(Odontologo entidad) {
        Odontologo odontologoAretornar =null;
        Connection connection = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entidad.getApellido());
            preparedStatement.setString(2,entidad.getNombre());
            preparedStatement.setString(3,entidad.getDni());
            preparedStatement.setDate(4,Date.valueOf(entidad.getFechaNacimiento()));
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next())
            {
                Integer id = resultSet.getInt(1);
                odontologoAretornar = new Odontologo(id,entidad.getApellido(),entidad.getNombre(),entidad.getDni(),entidad.getFechaNacimiento(), numeroMatricula);
            }
            LOGGER.info("Empleado persistido "+ odontologoAretornar);
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
    public Odontologo buscarPorId(Integer id) {

        Connection connection = null;
        Odontologo odontologoEncontrado =null;
        try
        {
            connection= H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Integer IdDevuelto = resultSet.getInt(1);
                String apellido=resultSet.getString(2);
                String nombre=resultSet.getString(3);
                String dni=resultSet.getString(4);
                LocalDate fecha=resultSet.getDate(5).toLocalDate();


                odontologoEncontrado = new Odontologo(IdDevuelto,apellido,nombre,dni,fecha, numeroMatricula);

                LOGGER.info("empleado encontrado "+ odontologoEncontrado);
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

        return odontologoEncontrado;
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
                String apellido=resultSet.getString(2);
                String nombre=resultSet.getString(3);
                String dni=resultSet.getString(4);
                LocalDate fecha=resultSet.getDate(5).toLocalDate();


                Odontologo odontologo = new Odontologo(IdDevuelto,apellido,nombre,dni,fecha, numeroMatricula);
                LOGGER.info("Empleado listado "+ odontologo);
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

    @Override
    public void borrarPorId(Integer id) {
        Connection connection = null;
        Odontologo odontologo = buscarPorId(id);
        try {

            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            Statement statment = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();

            LOGGER.info("Empleado borrado "+ odontologo);
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


    }

    @Override
    public Odontologo actualizar(Odontologo entidad) {
        Connection connection = null;
        Odontologo odontologoActualizado =null;
        try {

            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entidad.getApellido());
            preparedStatement.setString(2,entidad.getNombre());
            preparedStatement.setString(3,entidad.getDni());
            preparedStatement.setDate(4,Date.valueOf(entidad.getFechaNacimiento()));
            preparedStatement.setInt(5,entidad.getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();


            while(resultSet.next())
            {
                LOGGER.info("Result Set "+ resultSet);

                Integer IdDevuelto = resultSet.getInt(1);

                odontologoActualizado = new Odontologo(IdDevuelto,entidad.getApellido(), entidad.getNombre(), entidad.getDni(), entidad.getFechaNacimiento(), numeroMatricula);

        }
            LOGGER.info("Empleado actualizado "+ odontologoActualizado);
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
        return odontologoActualizado;
    }
}
