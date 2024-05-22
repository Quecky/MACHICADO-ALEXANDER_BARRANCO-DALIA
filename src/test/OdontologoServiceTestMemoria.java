package test;

import model.Odontologo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistencia.impl.OdontologoEnMemoria;
import service.OdontologoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTestMemoria {

    private static OdontologoService odontologoService = new OdontologoService(new OdontologoEnMemoria());

    @Test
    @DisplayName("Testear que un odontologo fue guardado")
    void testOdontologoGuardado(){
        Odontologo odontologo = new Odontologo("Cosme","Menganito","456464");
        Odontologo odontologoDesdeLaMemoria = odontologoService.registrarOdontologo(odontologo);

        assertNotNull(odontologoDesdeLaMemoria);
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

        assertEquals(4, odontologos.size());

    }

}