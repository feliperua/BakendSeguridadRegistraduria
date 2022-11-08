package SeguridadBack.Repositorios;
import SeguridadBack.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RepositorioPermiso extends MongoRepository<Permiso,String>
{

}