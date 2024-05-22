package test;

import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import service.EmpleadoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    private static EmpleadoService empleadoService=new EmpleadoService(new persistencia.impl.EmpleadoDaoH2());

    private static Logger LOGGER = Logger.getLogger(OdontologoServiceTest.class);
    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/parcial1;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
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
    @DisplayName("Testear empleado guardado")
    void testearEmpleadoGuardado() {
        Odontologo odontologo = new Odontologo("Aldo","Fefo","43434", LocalDate.of(2024,04,22), numeroMatricula);

        Odontologo odontologoDesdeLaBD = empleadoService.registrarEmpleado(odontologo);
        assertNotNull(odontologoDesdeLaBD);

    }

    @Test
    @DisplayName("Testear busqueda empleado por ID")
    void testsEmpleadoPorID() {
        Integer id = 1;
        Odontologo odontologoEncontrado =empleadoService.buscarPorId(id);
        assertEquals(id, odontologoEncontrado.getId());
    }


    @Test
    @DisplayName("Testear busqueda todos los empleados")
    void testBusquedaTodos() {
        Odontologo odontologo = new Odontologo("Cosme","Manganito","443",LocalDate.of(2024,12,13), numeroMatricula);
        empleadoService.registrarEmpleado(odontologo);

        List<Odontologo> odontologos = empleadoService.buscarTodos();
        assertEquals(1, odontologos.size());

    }

    @Test
    @DisplayName("Testear borrar por ID")
    void testBorrarPorID() {
        Odontologo odontologo1 = new Odontologo("Cosme","Manganito","443",LocalDate.of(2024,12,13), numeroMatricula);
        Odontologo odontologo2 = new Odontologo("Politan","Manganita","44443",LocalDate.of(2020,12,13), numeroMatricula);

        empleadoService.registrarEmpleado(odontologo1);
        empleadoService.registrarEmpleado(odontologo2);
        empleadoService.borrarPorId(2);

        List<Odontologo> odontologos = empleadoService.buscarTodos();

        assertEquals(1, odontologos.size());

    }


    @Test
    @DisplayName("Testear actualizar empleado")
    void testActualizar() {
        Odontologo odontologo1 = new Odontologo("Cosme","Manganito","443",LocalDate.of(2024,12,13), numeroMatricula);
        Odontologo odontologo2 = new Odontologo("Politan","Manganita","44443",LocalDate.of(2020,12,13), numeroMatricula);

        Odontologo odontologo3 = new Odontologo(1,"Paco","Manganita","44443",LocalDate.of(2020,12,13), numeroMatricula);

        empleadoService.registrarEmpleado(odontologo1);
        empleadoService.registrarEmpleado(odontologo2);
        empleadoService.actualizar(odontologo3);

        assertEquals("Paco",empleadoService.buscarPorId(1).getApellido());

    }


}