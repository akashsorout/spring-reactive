package in.co.bytehub.learn.reactive.repo;

import in.co.bytehub.learn.reactive.entity.Person;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonReactiveRepo extends ReactiveCassandraRepository<Person, String> {

}
