package edu.kit.cm.WorkspaceManagement.Utilization.Service;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryEntryJPA;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryEntryMapper;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryCrudRepository;
//import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryAdapter{

    private HistoryCrudRepository historyCrudRepository;
    private HistoryEntryMapper historyEntryMapper;

    public HistoryAdapter(HistoryCrudRepository historyCrudRepository) {
        this.historyCrudRepository = historyCrudRepository;
        this.historyEntryMapper = new HistoryEntryMapper();
    }


    public HistoryEntry createEntity(HistoryEntry entity) {
        HistoryEntryJPA entry = historyCrudRepository.save(historyEntryMapper.map(entity));
        return historyEntryMapper.map(entry);
    }

    public List<HistoryEntry> findAllEntitys() {
        List<HistoryEntry> list = new ArrayList<HistoryEntry>();
        for(HistoryEntryJPA historyEntryJpa: historyCrudRepository.findAll()){
            list.add(historyEntryMapper.map(historyEntryJpa));
        };
        return list;
    }

}
