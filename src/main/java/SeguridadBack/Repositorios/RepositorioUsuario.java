package SeguridadBack.Repositorios;
import SeguridadBack.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RepositorioUsuario extends MongoRepository<Usuario,String>
{

}