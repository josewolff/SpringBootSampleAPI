package repositories;
import entities.Companies;
import entities.Users;
import org.springframework.data.repository.CrudRepository;


public interface CompaniesRepository extends CrudRepository<Companies, Integer> {
    Iterable<Companies> findByCompanyname(String companyname);
}
