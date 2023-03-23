package webapp.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.porder.pojo.Porder;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Porder, Integer> {

}

