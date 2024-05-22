package test;

import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import persistencia.impl.OdontologoDaoH2;
import service.OdontologoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    private static OdontologoService odontologoService =new OdontologoService(new OdontologoDaoH2());

    private static Logger LOGGER = Logger.getLogger(OdontologoServiceTest.class);
    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/backend;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }



    @Test
    @DisplayName("Testear busqueda todos los odontologos")
    void testBusquedaTodos() {
        Odontologo odontologo1 = new Odontologo("Alex","Manganito","443" );
        Odontologo odontologo2 = new Odontologo("Emma","Manganito","443" );
        Odontologo odontologo3 = new Odontologo("Abner","Manganito","443" );

        odontologoService.registrarOdontologo(odontologo1);
        odontologoService.registrarOdontologo(odontologo2);
        odontologoService.registrarOdontologo(odontologo3);

        List<Odontologo> odontologos = odontologoService.buscarTodos();
        assertEquals(3, odontologos.size());

    }



}