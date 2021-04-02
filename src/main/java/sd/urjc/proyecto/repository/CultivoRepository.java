package sd.urjc.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import sd.urjc.proyecto.model.Cultivo;

@Service
public interface CultivoRepository extends JpaRepository<Cultivo, Long>{
	Optional<Cultivo> findById (long id);
	
	
}