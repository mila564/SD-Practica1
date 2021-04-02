package sd.urjc.proyecto.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sd.urjc.proyecto.model.Cultivo;
import sd.urjc.proyecto.repository.CultivoRepository;
import sd.urjc.proyecto.repository.TratamientoRepository;

@Controller
public class CultivoController {

	@Autowired
	    CultivoRepository repCultivos;
	 
	@Autowired 
		TratamientoRepository repTratamientos;
	
	@PostConstruct
	public void init() {
		repCultivos.save(new Cultivo("Tomate", "Cherry", "Valencia", LocalDate.parse("2020-06-27"), new ArrayList<>()));
		repCultivos.save(new Cultivo("Cerezo", "Lapins","Valle del Jerte",LocalDate.parse("2020-02-17"), new ArrayList<>()));
		repCultivos.save(new Cultivo("Cebolla", "Calcots","Barcelona", LocalDate.parse("2020-05-21"), new ArrayList<>()));
	}
	

	@RequestMapping("/cultivos")
	public String mostrarCultivos (Model model) {
		model.addAttribute("cultivos", repCultivos.findAll());
		return "cultivos";
	}
		
	@RequestMapping("/cultivos/nuevoCultivo")
	public String añadirCultivo (@RequestParam String nombre,
			@RequestParam String variedad,
			@RequestParam String zona,
			@RequestParam String fechaPlantacion, Model model) {
		LocalDate fechaPlant;
		DateTimeFormatter formatter; 
		Cultivo cultivo; 
		if ((!nombre.equals("")) && (!variedad.equals("")) && (!fechaPlantacion.equals(""))) {
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			fechaPlant = LocalDate.parse(fechaPlantacion, formatter);
			cultivo = new Cultivo(nombre,
					  variedad, 
					  zona,
					  fechaPlant);
			repCultivos.save(cultivo);
			return "creado_cultivo";
		}
		else {
			return "campos_erroneos_cultivos";
		}
	}
	 
	
	 @RequestMapping("/cultivos/modificar/{id}")
	 public String modificarCultivo(@PathVariable long id, Model model) {
	    Optional<Cultivo> opt = repCultivos.findById(id);
		if (opt.isPresent()) {
			model.addAttribute("cultivo", opt.get());
			return "modificar_cultivo";
		}
		else {
			return "cultivos";
		
		}
	 }
	 
	 @RequestMapping("cultivo/editar/{id}")
		public String editarProducto(@PathVariable String id, @RequestParam String nombre
				,@RequestParam String variedad
				,@RequestParam String zona
				,@RequestParam String fechaPlantacion, Model model) {
		 	LocalDate fechaPlant;
			DateTimeFormatter formatter;
			Optional<Cultivo> opt= repCultivos.findById(Long.parseLong(id));
			Cultivo cultivo;
			if ((opt.isPresent()) && (!nombre.equals("")) && (!variedad.equals("")) && (!fechaPlantacion.equals(""))) {
				formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				fechaPlant = LocalDate.parse(fechaPlantacion, formatter);
			    cultivo= opt.get();
				cultivo.setNombre(nombre);
				cultivo.setVariedad(variedad);
				cultivo.setZona(zona);
				cultivo.setFechaPlantacion(fechaPlant);
				repCultivos.save(cultivo);
				return "editado_cultivo";
			}
			else {
				return "campos_erroneos_cultivos";
			}
		}
	 @RequestMapping("/cultivos/mostrar/{id}")
	 public String mostrarCultivo(@PathVariable long id, Model model) {
	   	Cultivo cultivo = repCultivos.getOne(id);
	   	model.addAttribute("cultivo", cultivo);
        return "mostrar_cultivo";
	}
	 
	 @RequestMapping("/cultivos/borrar/{id}")
	public String borrarCultivo(@PathVariable long id, Model model){
		 Cultivo cultivo = repCultivos.getOne(id);
		 // model.addAttribute("cultivo", cultivo);
		 repCultivos.delete(cultivo);
	     return "borrado_cultivo";
		
	}
	
}
