package pro_db;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pro_db.Department;
import pro_db.Employee;
import pro_db.ManagesDepPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T23:41:49")
@StaticMetamodel(ManagesDep.class)
public class ManagesDep_ { 

    public static volatile SingularAttribute<ManagesDep, Department> department;
    public static volatile SingularAttribute<ManagesDep, Employee> employee;
    public static volatile SingularAttribute<ManagesDep, ManagesDepPK> managesDepPK;
    public static volatile SingularAttribute<ManagesDep, Date> startDate;

}