package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryCrudRepository extends CrudRepository<HistoryEntryJPA, Long> {
}
