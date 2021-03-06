package se.seqarc.samplersequencer.sample;

import org.springframework.data.repository.CrudRepository;
import se.seqarc.samplersequencer.category.Category;

import java.util.List;
import java.util.Optional;

public interface SampleRepository extends CrudRepository<Sample, Long> {

    Optional<List<Sample>> findSamplesByCategory(Category category);

    Optional<List<Sample>> findByNameContainingIgnoreCase(String searchphrase);

    Optional<List<Sample>> findByNameContainingIgnoreCaseAndCategory(String searchphrase, Category category);

    Optional<Sample> findFirstByChecksum(String checksum);
}
