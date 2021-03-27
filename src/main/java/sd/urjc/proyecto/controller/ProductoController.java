package sd.urjc.proyecto.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sd.urjc.proyecto.model.Producto;
import sd.urjc.proyecto.repository.ProductoRepository;

@Controller
public class ProductoController {

	@Autowired
	private ProductoRepository repProductos;
	
	@PostConstruct
	public void init() {
		repProductos.save(new Producto("Microtox", "Fungicida-acaricida a base de azufre en forma de gránulos dispersables en agua.", 10, 14));
		repProductos.save(new Producto("Captana", "Fungicida que actúa inhibiendo la germinación de las esporas, dificultando el crecimiento y desarrollo del micelo.", 9, 5));
		repProductos.save(new Producto("Adrex", "Miscible con todo tipo de productos y puede aplicarse sobre todos los cultivos.", 4, 8));
	}
	
	@RequestMapping(value="/")
	public String mostrarProductos (Model model) {
		model.addAttribute("productos", repProductos.findAll());
		return "productos";
	}
	
	@RequestMapping(value="/mostrarProducto")
	public String mostrarProducto (
						@RequestParam String nombre,
						Model model){
		Producto producto = repProductos.findByNombre(nombre);
		model.addAttribute("producto", producto);
		return "mostrarProducto";
	}
	
	@RequestMapping(value="/nuevoProducto")
	public String nuevoProducto (Producto producto,
						Model model) {
		repProductos.save(producto);
		return "creado";
	}
	
}
