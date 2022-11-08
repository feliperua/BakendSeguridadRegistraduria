package SeguridadBack.Repositorios;
import SeguridadBack.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RepositorioRol extends MongoRepository<Rol,String>
{

}