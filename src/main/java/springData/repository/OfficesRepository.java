package springData.repository;


import dataBase.entity.Office;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface OfficesRepository extends JpaRepository<Office, String> {

    @Cacheable("officeCashed")
    Set<Office> findByTargetBetween(BigDecimal minQty, BigDecimal maxQty);

}
