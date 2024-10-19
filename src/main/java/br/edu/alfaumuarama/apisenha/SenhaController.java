package br.edu.alfaumuarama.apisenha;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class SenhaController {

    @Autowired
    private SenhaRepository repository;

    //schema:autoridade:path/query/fragment
    //http://localhost:8080/senhas/{id}

    @GetMapping("/senhas/{id}")
    public ResponseEntity<Senha> findById(@PathVariable Long id){
        Optional<Senha> senhaOp = repository.findById(id);
        if(senhaOp.isPresent()){
            var senha = senhaOp.get();
            return ResponseEntity.ok(senha);
        }else{
            return ResponseEntity.notFound().build();
        }
    };

    @PostMapping("/senhas")
    public ResponseEntity save(@RequestBody Senha senha, UriComponentsBuilder uc) {
        Senha save = repository.save(senha);
        URI uri = uc.path("/senhas/{id}").buildAndExpand(save.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
}
