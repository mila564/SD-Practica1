package sd.urjc.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class TratamientoController {

    @Autowired
    TratamientoRepository repTratamientos;
    @Autowired
    CultivoRepository repCultivos;
    @Autowired
    ProductoRepository repProductos;

    @PostConstruct
    public void init() {
        Producto producto;
        Cultivo cultivo;
        LocalDate fecha;

        // Los tres primeros tratamientos se aplicaron en la misma fecha
        // y sobre el mismo cultivo
        cultivo = repCultivos.findByNombre("Tomate");
        fecha = LocalDate.parse("2020-09-20");

        producto = repProductos.findByNombre("Microtox");
        repTratamientos.save(new Tratamiento(
            cultivo,
            producto,
            "H8486688",
            fecha,
            fecha.plusDays(
                producto.getPlazoReentrada()
            ),
            fecha.plusDays(
                producto.getPlazoRecoleccion()
            )
        ));

        producto = repProductos.findByNombre("Captana");
        repTratamientos.save(new Tratamiento(
                cultivo,
                producto,
                "F4593876",
                fecha,
                fecha.plusDays(
                        producto.getPlazoReentrada()
                ),
                fecha.plusDays(
                        producto.getPlazoRecoleccion()
                )
        ));

        producto = repProductos.findByNombre("Fruitel");
        repTratamientos.save(new Tratamiento(
                cultivo,
                producto,
                "A0245872",
                fecha,
                fecha.plusDays(
                        producto.getPlazoReentrada()
                ),
                fecha.plusDays(
                        producto.getPlazoRecoleccion()
                )
        ));

        // Dos tratamientos sobre el mismo cultivo pero lo demás diferente
        cultivo = repCultivos.findByNombre("Alcachofa");

        producto = repProductos.findByNombre("Adrex");
        fecha = LocalDate.parse("2021-02-15");
        repTratamientos.save(new Tratamiento(
                cultivo,
                producto,
                "G7834097",
                fecha,
                fecha.plusDays(
                        producto.getPlazoReentrada()
                ),
                fecha.plusDays(
                        producto.getPlazoRecoleccion()
                )
        ));

        producto = repProductos.findByNombre("Microtox");
        fecha = LocalDate.parse("2021-03-11");
        repTratamientos.save(new Tratamiento(
            cultivo,
            producto,
            "I9873597",
            fecha,
            fecha.plusDays(
                producto.getPlazoReentrada()
            ),
            fecha.plusDays(
                producto.getPlazoRecoleccion()
            )
        ));

        // Un solo tratamiento ambos plazos
        producto = repProductos.findByNombre("Captana");
        cultivo = repCultivos.findByNombre("Manzana");
        fecha = LocalDate.parse("2019-07-06");
        repTratamientos.save(new Tratamiento(
                cultivo,
                producto,
                "U5430894",
                fecha,
                fecha.plusDays(
                        producto.getPlazoReentrada()
                ),
                fecha.plusDays(
                        producto.getPlazoRecoleccion()
                )
        ));

        // Un solo tratamiento sin plazo de reentrada
        producto = repProductos.findByNombre("Fruitel");
        cultivo = repCultivos.findByNombre("Cereza");
        fecha = LocalDate.parse("2020-03-21");
        repTratamientos.save(new Tratamiento(
                cultivo,
                producto,
                "Y0387508",
                fecha,
                fecha.plusDays(
                        producto.getPlazoReentrada()
                ),
                fecha.plusDays(
                        producto.getPlazoRecoleccion()
                )
        ));

        // Un solo tratamiento sin ningún plazo
        producto = repProductos.findByNombre("Poltix");
        cultivo = repCultivos.findByNombre("Cebolla");
        fecha = LocalDate.parse("2020-10-28");
        repTratamientos.save(new Tratamiento(
                cultivo,
                producto,
                "S0823472",
                fecha,
                fecha.plusDays(
                        producto.getPlazoReentrada()
                ),
                fecha.plusDays(
                        producto.getPlazoRecoleccion()
                )
        ));
    }

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
    		return "campos_erroneos_tratamientos.html";
    	}

        // Parseo de IDs y fecha inicial
        long idCultivoNum = Long.parseLong(idCultivo);
        long idProductoNum= Long.parseLong(idProducto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicioTrat = LocalDate.parse(inicioTratamiento, formatter);

        // Cálculo de fechas de fin de plazos
        Producto producto = repProductos.getOne(idProductoNum);
    	LocalDate finPlazoSeg = inicioTrat.plusDays(producto.getPlazoReentrada());;
    	LocalDate finPlazoNoRec = inicioTrat.plusDays(producto.getPlazoRecoleccion());;

        // Creación del tratamiento
        Cultivo cultivo = repCultivos.getOne(idCultivoNum);
        Tratamiento tratamiento = new Tratamiento(
                cultivo,
                producto,
                numLoteProducto,
                inicioTrat,
                finPlazoSeg,
                finPlazoNoRec
        );
        repTratamientos.save(tratamiento);

        return "tratamientoCreado.html";
    }

    @RequestMapping(value="/tratamiento/formulario_crear")
    public String formularioCrearTratamiento(Model model) {
        List<Cultivo> listaCultivos = repCultivos.findAll();
        List<Producto> listaProductos = repProductos.findAll();
        model.addAttribute("cultivos", listaCultivos);
        model.addAttribute("productos", listaProductos);
        return "crearTratamiento.html";
    }

    @RequestMapping("/tratamiento/{id}")
    public String mostrarTratamiento(@PathVariable long id, Model model) {
        Tratamiento tratamiento = repTratamientos.getOne(id);
        Cultivo cultivo = tratamiento.getCultivo();
        Producto producto = tratamiento.getProducto();
        model.addAttribute("tratamiento", tratamiento);
        model.addAttribute("cultivo", cultivo);
        model.addAttribute("producto", producto);
        return "mostrarTratamiento.html";
    }

    @RequestMapping("/tratamiento")
    public String tratamientos(Model model) {
    	List<Tratamiento> listaTratamientos = repTratamientos.findAll();
    	model.addAttribute("tratamientos", listaTratamientos);
    	return"tratamientos.html";
    }

    @RequestMapping("/tratamiento/modificar/{id}")
    public String modificarFormularioTratamiento(@PathVariable long id, Model model) {
    	Tratamiento tratamiento = repTratamientos.getOne(id);
    	Cultivo cultivo = tratamiento.getCultivo();
    	Producto producto = tratamiento.getProducto();
        List<Cultivo> listaCultivos = repCultivos.findAll();
        List<Producto> listaProductos = repProductos.findAll();
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
            return "campos_erroneos_tratamientos.html";
        }

        // Parseo de IDs y fecha inicial
        long idCultivoNum = Long.parseLong(idCultivo);
        long idProductoNum= Long.parseLong(idProducto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicioTrat = LocalDate.parse(inicioTratamiento, formatter);

        // Cálculo de fechas de fin de plazos
        Producto producto = repProductos.getOne(idProductoNum);
        LocalDate finPlazoSeg = inicioTrat.plusDays(producto.getPlazoReentrada());
        LocalDate finPlazoNoRec = inicioTrat.plusDays(producto.getPlazoRecoleccion());

        // Modificación del tratamiento
        Cultivo cultivo = repCultivos.getOne(idCultivoNum);
        Tratamiento tratamientoMod = repTratamientos.getOne(idTratamiento);
        tratamientoMod.setCultivo(cultivo);
        tratamientoMod.setProducto(producto);
        tratamientoMod.setNumLoteProducto(numLoteProducto);
        tratamientoMod.setInicioTratamiento(inicioTrat);
        tratamientoMod.setFinPlazoReentrada(finPlazoSeg);
        tratamientoMod.setFinPlazoRecoleccion(finPlazoNoRec);
        repTratamientos.save(tratamientoMod);

    	return "tratamientoModificado.html";
    }

    @RequestMapping("/tratamiento/borrar/{id}")
    public String borrar(@PathVariable long id) {
        Tratamiento tratamiento = repTratamientos.getOne(id);
        repTratamientos.deleteById(id);
        return "borrado_tratamiento.html";
    }

    @RequestMapping("/tratamiento/filtro")
    public String filtrarTratamientos(Model model, String fechaIntroducida, String orden) {
    	List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
    	LocalDate fechaFiltrado = LocalDate.parse(fechaIntroducida);
    	//Tratamiento de error
    	if((orden == null) && fechaIntroducida.equals("")) {
        	return "campos_erroneos_filtrar_tratamientos.html";
        }
    	//Ordenado
    	else if (orden != null && fechaIntroducida.equals("")) {
        	switch(orden) {
    			case "especie":
    					tratamientos = repTratamientos.findByOrderByCultivoNombreAsc();
    					break;
    			case "fechaReentrada":
    					tratamientos = repTratamientos.findByOrderByFinPlazoReentradaAsc();
    					break;
    			case "fechaRecoleccion":
    					tratamientos = repTratamientos.findByOrderByFinPlazoRecoleccionAsc();
        	}
        }
    	//Filtrado 
    	else if (orden == null && !fechaIntroducida.equals("")) {
        	tratamientos = repTratamientos.findAllByFinPlazoReentradaGreaterThanEqualOrFinPlazoRecoleccionGreaterThanEqual(fechaFiltrado, fechaFiltrado);
        }
    	//Ordenado y filtrado
    	else {
    		switch(orden) {
			case "especie":
					tratamientos = repTratamientos.findAllByFinPlazoReentradaGreaterThanEqualOrFinPlazoRecoleccionGreaterThanEqualOrderByCultivoNombreAsc(fechaFiltrado, fechaFiltrado);
					break;
			case "fechaReentrada":
					tratamientos = repTratamientos.findAllByFinPlazoReentradaGreaterThanEqualOrFinPlazoRecoleccionGreaterThanEqualOrderByFinPlazoReentradaAsc(fechaFiltrado, fechaFiltrado);
					break;
			case "fechaRecoleccion":
					tratamientos = repTratamientos.findAllByFinPlazoReentradaGreaterThanEqualOrFinPlazoRecoleccionGreaterThanEqualOrderByFinPlazoRecoleccionAsc(fechaFiltrado, fechaFiltrado);
    	}
    	}
        model.addAttribute("tratamientos", tratamientos);
        
        return "tratamientos.html";
    }
}
