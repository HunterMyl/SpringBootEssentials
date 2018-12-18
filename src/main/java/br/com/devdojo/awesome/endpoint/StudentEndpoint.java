package br.com.devdojo.awesome.endpoint;

import br.com.devdojo.awesome.error.CustomErrorType;
import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.awesome.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("students")
public class StudentEndpoint {

    private final StudentRepository studentDao;
    @Autowired
    public StudentEndpoint(StudentRepository studentDao){
        this.studentDao = studentDao;
    }

    @GetMapping
    //@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listAll(){
        //System.out.println("Lista completa: " + dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(studentDao.findAll(), HttpStatus.OK);
    }
    @GetMapping(path = "/{id}")
    //@RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id){
        Optional<Student> student = studentDao.findById(id);
        //System.out.println("Consultado id "+id+" :" + dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        if(student == null)
            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable("name") String name){
        return new ResponseEntity<>(studentDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Student student){
        return new ResponseEntity<>(studentDao.save(student), HttpStatus.OK);
    }
    @DeleteMapping(path= "/{id}")
    //@RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        studentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping
    //@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Student student){
        studentDao.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
