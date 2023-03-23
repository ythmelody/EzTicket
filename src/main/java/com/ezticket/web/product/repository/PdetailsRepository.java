package webapp.pdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.pdetails.pojo.Pdetails;
import webapp.pdetails.pojo.PdetailsPK;

@Repository
public interface PdetailsRepository extends JpaRepository<Pdetails, PdetailsPK> {


}

