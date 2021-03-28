package sd.urjc.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sd.urjc.proyecto.model.Cultivo;
import sd.urjc.proyecto.model.Producto;
import sd.urjc.proyecto.model.Tratamiento;
import sd.urjc.proyecto.repository.CultivoRepository;
import sd.urjc.proyecto.repository.ProductoRepository;
import sd.urjc.proyecto.repository.TratamientoRepository;

import javax.annotation.PostConstruct;
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
    public String crearTramiento(@RequestParam Tratamiento tratamiento) {
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
}
