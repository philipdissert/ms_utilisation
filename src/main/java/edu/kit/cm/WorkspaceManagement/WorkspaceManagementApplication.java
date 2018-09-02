package edu.kit.cm.WorkspaceManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories
//@ComponentScan
@SpringBootApplication
public class WorkspaceManagementApplication {


	public static void main(String[] args) {
		SpringApplication.run(WorkspaceManagementApplication.class, args);

		//new HistoryAdapter().createEntity(new HistoryEntry(2,LocalDateTime.of(1,1,1,1,1,1),"PC"));
		//upa.createEntity(new HistoryEntry(2,LocalDateTime.of(1,1,1,1,1,1),"PC"));


	}
}