package edu.kit.cm.ms_utilisation.Utilization.Infrastructure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestDataBase {
    @Id
    @GeneratedValue
    private long id;
    private String name;
}
