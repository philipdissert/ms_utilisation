package edu.kit.cm.WorkspaceManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.RepositoryThread;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.UtilizationThread;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryEntryMapper;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.HistoryAdapter;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.ComputerStateATISAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.time.LocalDateTime;

//@EnableJpaRepositories
//@ComponentScan
@SpringBootApplication
public class WorkspaceManagementApplication {


	public static void main(String[] args) {
		SpringApplication.run(WorkspaceManagementApplication.class, args);

		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
		try {
			csaa.generatePoolElementsFromWorkspace();
			csaa.updateComputersWithStatesFromATIS();
			utilizationAdapter.updateSeats();
			//csaa.updateFreeSeatsFromATIS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UtilizationThread ut = new UtilizationThread();
		ut.start();


		//new HistoryAdapter().createEntity(new HistoryEntry(2,LocalDateTime.of(1,1,1,1,1,1),"PC"));
		//upa.createEntity(new HistoryEntry(2,LocalDateTime.of(1,1,1,1,1,1),"PC"));


	}
}