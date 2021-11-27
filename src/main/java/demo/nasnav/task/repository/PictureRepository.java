package demo.nasnav.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.nasnav.task.models.Uploads;
import demo.nasnav.task.models.Status;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Uploads, Integer> {
    List<Uploads> findAllByStatus(Status status);
}
