package sd.urjc.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import sd.urjc.proyecto.model.Producto;

@Service
public interface ProductoRepository extends JpaRepository<Producto, Long>{

	Producto findByNombre(String nombre);
	
}
