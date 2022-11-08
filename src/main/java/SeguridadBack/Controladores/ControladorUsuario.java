package SeguridadBack.Controladores;
import SeguridadBack.Modelos.Usuario;
import SeguridadBack.Repositorios.RepositorioUsuario;
import SeguridadBack.Modelos.Rol;
import SeguridadBack.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {
    @Autowired //poder crear el repositorio
    private RepositorioUsuario miRepositorioUsuario;
    @Autowired
    private RepositorioRol miRepositorioRol;


    @GetMapping("")
    public List<Usuario> index(){
        return this.miRepositorioUsuario.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody  Usuario infoUsuario){
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        return  this.miRepositorioUsuario.save(infoUsuario);


    }
    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        Usuario usuarioActual=this.miRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual==null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El usuario no fue ecnontrado");
        }
        return usuarioActual;
    }
    @PutMapping("{id}")
    public Usuario update(@PathVariable String id,@RequestBody  Usuario infoUsuario){
        Usuario usuarioActual=this.miRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El usuario no actualizo");
        }
        if (usuarioActual!=null){
            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioActual.setCorreo(infoUsuario.getCorreo());
            usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            return this.miRepositorioUsuario.save(usuarioActual);
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Usuario usuarioActual=this.miRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El usuario no se elimino");
        }
        if (usuarioActual!=null){
            this.miRepositorioUsuario.delete(usuarioActual);
        }
    }

    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id,@PathVariable String id_rol){
        Usuario usuarioActual=this.miRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El usuario solicitado no existe");
        }
        Rol rolActual=this.miRepositorioRol.findById(id_rol).orElse(null);
        if(rolActual==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El rol solicitado no existe");
        }

        usuarioActual.setRol(rolActual);
        return this.miRepositorioUsuario.save(usuarioActual);


    }
    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


}