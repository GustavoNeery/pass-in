package nry.com.pass_in.repositories;

import nry.com.pass_in.domain.checkIn.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}
