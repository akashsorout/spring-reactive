package in.co.bytehub.learn.reactive.repo;

import in.co.bytehub.learn.reactive.entity.Person;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface PersonRepo extends CassandraRepository<Person, String> {

}
