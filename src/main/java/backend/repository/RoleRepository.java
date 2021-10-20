package backend.repository;

import backend.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Role findOneByName(String name);

    List<Role> findByNameIn(String[] names);

}
