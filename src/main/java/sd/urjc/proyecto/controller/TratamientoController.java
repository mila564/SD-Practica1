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

    	LocalDate inicioTrat;
    	LocalDate finPlazoSeg;
    	LocalDate finPlazoNoRec;
        long idCultivoNum = Long.parseLong(idCultivo);
        long idProductoNum= Long.parseLong(idProducto);
        Producto producto = productoRepository.getOne(idProductoNum);

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	inicioTrat = LocalDate.parse(inicioTratamiento, formatter);
    	finPlazoSeg = inicioTrat.plusDays(producto.getPlazoReentrada());
    	finPlazoNoRec = inicioTrat.plusDays(producto.getPlazoRecoleccion());
    
    	Tratamiento tratamiento = new Tratamiento(
    	        idCultivoNum,
                idProductoNum,
                numLoteProducto,
                inicioTrat,
                finPlazoSeg,
                finPlazoNoRec
        );
        tratamientoRepository.save(tratamiento);

        Cultivo cultivo = cultivoRepository.getOne(idCultivoNum);
        List<Tratamiento> tratamientos = cultivo.getTratamientos();
        tratamientos.add(tratamiento);
        cultivoRepository.save(cultivo);
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
        Cultivo cultivo = cultivoRepository.getOne(tratamiento.getIdCultivo());
        Producto producto = productoRepository.getOne(tratamiento.getIdProducto());
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
    	long idCultivo = tratamiento.getIdCultivo();
    	long idProducto = tratamiento.getIdProducto();
    	Cultivo cultivo = cultivoRepository.getOne(idCultivo);
    	Producto producto = productoRepository.getOne(idProducto);
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

        // Actualización de la lista de tratamientos del cultivo del tratamiento original
        Tratamiento tratamientoOriginal = tratamientoRepository.getOne(idTratamiento);
        Cultivo cultivoOriginal = cultivoRepository.getOne(
                tratamientoOriginal.getIdCultivo()
        );
        List<Tratamiento> tratamientosCultOrig = cultivoOriginal.getTratamientos();
        tratamientosCultOrig.remove(tratamientoOriginal);
        cultivoRepository.save(cultivoOriginal);

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
        Tratamiento tratamientoMod = tratamientoRepository.getOne(idTratamiento);
        tratamientoMod.setIdCultivo(idCultivoNum);
        tratamientoMod.setIdProducto(idProductoNum);
        tratamientoMod.setNumLoteProducto(numLoteProducto);
        tratamientoMod.setInicioTratamiento(inicioTrat);
        tratamientoMod.setFinPlazoSeguridad(finPlazoSeg);
        tratamientoMod.setFinPlazoNoRecoleccion(finPlazoNoRec);
        tratamientoRepository.save(tratamientoMod);

        // Guardar el tratamiento modificado en la lista del cultivo del
        // tratamiento modificado
        Cultivo cultivoMod = cultivoRepository.getOne(idCultivoNum);
        List<Tratamiento> tratamientosCultMod = cultivoMod.getTratamientos();
        tratamientosCultMod.add(tratamientoMod);
        cultivoRepository.save(cultivoMod);

    	return "tratamientoModificado.html";
    }

    @RequestMapping("/tratamiento/borrar/{id}")
    public String borrar(@PathVariable long id) {
        Tratamiento tratamiento = tratamientoRepository.getOne(id);
        Cultivo cultivo = cultivoRepository.getOne(
                tratamiento.getIdCultivo()
        );
        List<Tratamiento> tratamientos = cultivo.getTratamientos();
        tratamientos.remove(tratamiento);

        cultivoRepository.save(cultivo);
        tratamientoRepository.deleteById(id);
        return "borradoConExito.html";
    }
}
