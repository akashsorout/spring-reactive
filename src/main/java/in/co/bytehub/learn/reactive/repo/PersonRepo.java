package in.co.bytehub.learn.reactive.repo;

import in.co.bytehub.learn.reactive.entity.Person;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends CassandraRepository<Person, String> {

}
