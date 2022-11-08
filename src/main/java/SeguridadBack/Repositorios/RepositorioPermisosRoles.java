package SeguridadBack.Repositorios;
import SeguridadBack.Modelos.PermisosRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RepositorioPermisosRoles extends MongoRepository<PermisosRoles,String>
{

}