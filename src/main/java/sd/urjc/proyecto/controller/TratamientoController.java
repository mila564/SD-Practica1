package sd.urjc.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.LongArraySerializer;

import sd.urjc.proyecto.model.Cultivo;
import sd.urjc.proyecto.model.Producto;
import sd.urjc.proyecto.model.Tratamiento;
import sd.urjc.proyecto.repository.CultivoRepository;
import sd.urjc.proyecto.repository.ProductoRepository;
import sd.urjc.proyecto.repository.TratamientoRepository;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TratamientoController {

    @Autowired
    TratamientoRepository tratamientoRepository;
    @Autowired
    CultivoRepository cultivoRepository;
    @Autowired
    ProductoRepository productoRepository;

    /*@PostConstruct
    public void init() {
        tratamientoRepository.save(new Tratamiento());
    }*/

    @RequestMapping(value="/tratamiento/crear")
    public String crearTramiento(
            @RequestParam String idCultivo,
            @RequestParam String idProducto,
            @RequestParam String numLoteProducto,
            @RequestParam String inicioTratamiento
    ) {
    	if(
    	        inicioTratamiento.equals("") ||
                numLoteProducto.equals("")
        ) {
    		return "errorTratamiento.html";
    	}

        // Parseo de IDs y fecha inicial
        long idCultivoNum = Long.parseLong(idCultivo);
        long idProductoNum= Long.parseLong(idProducto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicioTrat = LocalDate.parse(inicioTratamiento, formatter);

        // Cálculo de fechas de fin de plazos
        Producto producto = productoRepository.getOne(idProductoNum);
    	LocalDate finPlazoSeg = inicioTrat.plusDays(producto.getPlazoReentrada());;
    	LocalDate finPlazoNoRec = inicioTrat.plusDays(producto.getPlazoRecoleccion());;

        // Creación del tratamiento
        Cultivo cultivo = cultivoRepository.getOne(idCultivoNum);
        Tratamiento tratamiento = new Tratamiento(
                cultivo,
                producto,
                numLoteProducto,
                inicioTrat,
                finPlazoSeg,
                finPlazoNoRec
        );
        tratamientoRepository.save(tratamiento);

        return "tratamientoCreado.html";
    }

    @RequestMapping(value="/tratamiento/formulario_crear")
    public String formularioCrearTratamiento(Model model) {
        List<Cultivo> listaCultivos = cultivoRepository.findAll();
        List<Producto> listaProductos = productoRepository.findAll();
        model.addAttribute("cultivos", listaCultivos);
        model.addAttribute("productos", listaProductos);
        return "crearTratamiento.html";
    }

    @RequestMapping("/tratamiento/{id}")
    public String mostrarTratamiento(@PathVariable long id, Model model) {
        Tratamiento tratamiento = tratamientoRepository.getOne(id);
        Cultivo cultivo = tratamiento.getCultivo();
        Producto producto = tratamiento.getProducto();
        model.addAttribute("tratamiento", tratamiento);
        model.addAttribute("cultivo", cultivo);
        model.addAttribute("producto", producto);
        return "mostrarTratamiento.html";
    }

    @RequestMapping("/tratamiento")
    public String tratamientos(Model model) {
    	List<Tratamiento> listaTratamientos = tratamientoRepository.findAll();
    	model.addAttribute("tratamientos", listaTratamientos);
    	return"tratamientos.html";
    }

    @RequestMapping("/tratamiento/modificar/{id}")
    public String modificarFormularioTratamiento(@PathVariable long id, Model model) {
    	Tratamiento tratamiento = tratamientoRepository.getOne(id);
    	Cultivo cultivo = tratamiento.getCultivo();
    	Producto producto = tratamiento.getProducto();
        List<Cultivo> listaCultivos = cultivoRepository.findAll();
        List<Producto> listaProductos = productoRepository.findAll();
    	model.addAttribute("tratamientoOriginal", tratamiento);
    	model.addAttribute("cultivoOriginal", cultivo);
    	model.addAttribute("productoOriginal", producto);
        model.addAttribute("cultivos", listaCultivos);
        model.addAttribute("productos", listaProductos);
    	return "modificarFormularioTratamiento.html";
    }

    @RequestMapping("/tratamiento/modificar-objeto/{idTratamiento}")
    public String modificarTratamiento(
            @PathVariable long idTratamiento,
            @RequestParam String idCultivo,
            @RequestParam String idProducto,
            @RequestParam String numLoteProducto,
            @RequestParam String inicioTratamiento
    ) {
        // Gestión de errores: usuario introduce cadena vacía en el formulario
        if(
                inicioTratamiento.equals("") ||
                numLoteProducto.equals("")
        ) {
            return "errorTratamiento.html";
        }

        // Parseo de IDs y fecha inicial
        long idCultivoNum = Long.parseLong(idCultivo);
        long idProductoNum= Long.parseLong(idProducto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicioTrat = LocalDate.parse(inicioTratamiento, formatter);

        // Cálculo de fechas de fin de plazos
        Producto producto = productoRepository.getOne(idProductoNum);
        LocalDate finPlazoSeg = inicioTrat.plusDays(producto.getPlazoReentrada());
        LocalDate finPlazoNoRec = inicioTrat.plusDays(producto.getPlazoRecoleccion());

        // Modificación del tratamiento
        Cultivo cultivo = cultivoRepository.getOne(idCultivoNum);
        Tratamiento tratamientoMod = tratamientoRepository.getOne(idTratamiento);
        tratamientoMod.setCultivo(cultivo);
        tratamientoMod.setProducto(producto);
        tratamientoMod.setNumLoteProducto(numLoteProducto);
        tratamientoMod.setInicioTratamiento(inicioTrat);
        tratamientoMod.setFinPlazoSeguridad(finPlazoSeg);
        tratamientoMod.setFinPlazoNoRecoleccion(finPlazoNoRec);
        tratamientoRepository.save(tratamientoMod);

    	return "tratamientoModificado.html";
    }

    @RequestMapping("/tratamiento/borrar/{id}")
    public String borrar(@PathVariable long id) {
        Tratamiento tratamiento = tratamientoRepository.getOne(id);
        tratamientoRepository.deleteById(id);
        return "borradoConExito.html";
    }
}
