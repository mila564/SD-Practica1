package sd.urjc.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import sd.urjc.proyecto.model.Producto;

@Service
public interface ProductoRepository extends JpaRepository<Producto, Long>{
	Optional<Producto> findById (long id);
	Producto findByNombre(String nombre);
}
