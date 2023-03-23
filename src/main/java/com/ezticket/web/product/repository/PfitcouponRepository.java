package webapp.pfitcoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.pfitcoupon.pojo.Pfitcoupon;
import webapp.pfitcoupon.pojo.PfitcouponPK;

@Repository
public interface PfitcouponRepository extends JpaRepository<Pfitcoupon, PfitcouponPK> {

}
