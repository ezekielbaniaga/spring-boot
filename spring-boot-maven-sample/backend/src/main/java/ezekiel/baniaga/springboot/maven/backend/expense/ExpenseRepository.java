package ezekiel.baniaga.springboot.maven.backend.expense;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByArchivedIsNullOrArchivedIsFalse();

    Optional<Expense> findByUniqueId(UUID uniqueId);

    List<Expense> findAllByArchivedIsTrue();

    // Using Native Query as JPQL does not support LIMIT in DELETE
    @Modifying
    @Query(value = """
        DELETE FROM expense
        WHERE id IN (
            SELECT id FROM expense
            WHERE archived = TRUE AND archived_at < :cutoff
            LIMIT :limit
        )
    """, nativeQuery = true)
    int deleteOldExpense(@Param("cutoff")LocalDateTime cutoff, @Param("limit") int limit);
}
