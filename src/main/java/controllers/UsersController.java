package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.UsersRepository;
import entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@RequestMapping(path="/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity<String> addNewUser (@RequestBody Users userToAdd) {
        Users user = new Users();
        user.setUsername(userToAdd.getUsername());
        user.setFirstname(userToAdd.getFirstname());
        user.setLastname(userToAdd.getLastname());
        user.setEmail(userToAdd.getEmail());
        usersRepository.save(user);
        return new ResponseEntity("{\"status\": \"ok\",\"message\": \"User added. id: " +
                user.getId() +", email:  "+ user.getEmail() + "\" }",
                HttpStatus.OK);
    }

    @GetMapping(path="/all")
    public  @ResponseBody Iterable<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping(path="/findbyid/{id}")
    public @ResponseBody ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        return usersRepository.findById(id).isPresent() ? new ResponseEntity(usersRepository.findById(id),HttpStatus.OK)
                : new ResponseEntity("{}",HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="/findbyemail/{email}")
    public  ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
        return usersRepository.findByEmail(email) != null ? new ResponseEntity(usersRepository.findByEmail(email),HttpStatus.OK)
                : new ResponseEntity("{}",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/removebyid/{id}")
    public ResponseEntity<String> removeById(@PathVariable Integer id) {
        boolean isTheUserInDb = usersRepository.findById(id).isPresent();
        if(isTheUserInDb){
            Users users = usersRepository.findById(id).get();
            String email = users.getEmail();
            usersRepository.deleteById(id);
            return new ResponseEntity("{\"status\": \"ok\",\"message\": \"User with id: " + id + " and email:  " + email + " was deleted. \" }",HttpStatus.OK);
        }else{
            return new ResponseEntity("{\"status\": \"notFound\",\"message\": \"notFound\" }",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/removeall")
    public ResponseEntity<String> removeAll() {
        usersRepository.deleteAll();
        return new ResponseEntity("{\"status\": \"ok\",\"message\": \"All users were deleted.\" }",HttpStatus.OK);
    }
}
