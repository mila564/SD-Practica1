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
    public String crearTramiento(@RequestParam String idCultivo
    							,@RequestParam String idProducto
    							,@RequestParam String numLoteProducto
    							,@RequestParam String inicioTratamiento
    							,@RequestParam String finPlazoSeguridad
    							,@RequestParam String finPlazoNoRecoleccion
    							,Model model)
    {
    	if(inicioTratamiento.equals("")|finPlazoSeguridad.equals("")|finPlazoNoRecoleccion.equals("")|numLoteProducto.equals("")) {
    		return "errorTratamiento.html";
    	}
    	LocalDate inicioTrat;
    	LocalDate finPlazoSeg;
    	LocalDate finPlazoNoRec;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	inicioTrat = LocalDate.parse(inicioTratamiento, formatter);
    	finPlazoSeg = LocalDate.parse(finPlazoSeguridad, formatter);
    	finPlazoNoRec = LocalDate.parse(finPlazoNoRecoleccion, formatter);
    	long idCultivoNum = Long.parseLong(idCultivo);
    	long idProductoNum= Long.parseLong(idProducto);
    
    	Tratamiento tratamiento = new Tratamiento(idCultivoNum,
    											  idProductoNum, 
    											  numLoteProducto,
    											  inicioTrat, 
    											  finPlazoSeg, 
    											  finPlazoNoRec);
        tratamientoRepository.save(tratamiento);
        return "tratamientoCreado.html";
    }

    @RequestMapping(value="/tratamiento/formulario_crear")
    public String formularioCrearTratamiento(Model model) {
        //Esto es lo correcto pero no tenemos todavía Cultivo
        List<Cultivo> listaCultivos = cultivoRepository.findAll();
        //Esto es un "placeholder"
        //List<Cultivo> listaCultivos = new ArrayList<>();
        List<Producto> listaProductos = productoRepository.findAll();
        model.addAttribute("cultivos", listaCultivos);
        model.addAttribute("productos", listaProductos);
        return "crearTratamiento.html";
    }
    @RequestMapping("/tratamiento/{id}")
    public String mostrarTratamiento(@PathVariable long id, Model model) {
        Tratamiento tratamiento = tratamientoRepository.getOne(id);
        model.addAttribute("tratamiento", tratamiento);
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
    	model.addAttribute("tratamiento", tratamiento);
    	return "modificarFormularioTratamiento.html";
    	
    }
    @RequestMapping("/tratamiento/modificar")
    public String modificarTratamiento() {
    	return "tratamientoModificado.html";
    }
    
}
