package controllers;

import entities.Companies;
import entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repositories.CompaniesRepository;


@Controller
@RequestMapping(path="/companies")
public class CompaniesController {

    @Autowired
    private CompaniesRepository companiesRepository;

    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity<String> addNewUser (@RequestBody Companies companyToAdd) {
        Companies company = new Companies();
        company.setCompanyname(companyToAdd.getCompanyname());
        company.setStreet(companyToAdd.getStreet());
        company.setPhonenumber(companyToAdd.getPhonenumber());
        companiesRepository.save(company);
        return new ResponseEntity("{\"status\": \"ok\",\"message\": \"Company added. id: " +
                company.getId() +", companyName:  "+ company.getCompanyname() + "\" }",
                HttpStatus.OK);
    }

    @GetMapping(path="/all")
    public  @ResponseBody Iterable<Companies> getAllUsers() {
        return companiesRepository.findAll();
    }

    @GetMapping(path="/findbyid/{id}")
    public @ResponseBody ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        return companiesRepository.findById(id).isPresent() ? new ResponseEntity(companiesRepository.findById(id),HttpStatus.OK)
                : new ResponseEntity("{}",HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="/findbyname/{companyname}")
    public  ResponseEntity<Users> getCompaniesByName(@PathVariable String companyname) {
        return companiesRepository.findByCompanyname(companyname) != null ? new ResponseEntity(companiesRepository.findByCompanyname(companyname),HttpStatus.OK)
                : new ResponseEntity("{}",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/removebyid/{id}")
    public ResponseEntity<String> removeById(@PathVariable Integer id) {
        boolean isTheCompanyInDb = companiesRepository.findById(id).isPresent();
        if(isTheCompanyInDb){
            Companies company = companiesRepository.findById(id).get();
            String companyname = company.getCompanyname();
            companiesRepository.deleteById(id);
            return new ResponseEntity("{\"status\": \"ok\",\"message\": \"Company with id: " + id + " and name:  " + companyname + " was deleted. \" }",HttpStatus.OK);
        }else{
            return new ResponseEntity("{\"status\": \"notFound\",\"message\": \"notFound\" }",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/removeall")
    public ResponseEntity<String> removeAll() {
        companiesRepository.deleteAll();
        return new ResponseEntity("{\"status\": \"ok\",\"message\": \"All companies were deleted.\" }",HttpStatus.OK);
    }
}
