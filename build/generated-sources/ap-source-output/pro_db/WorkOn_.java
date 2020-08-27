package pro_db;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pro_db.Employee;
import pro_db.Products;
import pro_db.WorkOnPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T23:41:49")
@StaticMetamodel(WorkOn.class)
public class WorkOn_ { 

    public static volatile SingularAttribute<WorkOn, BigInteger> hours;
    public static volatile SingularAttribute<WorkOn, WorkOnPK> workOnPK;
    public static volatile SingularAttribute<WorkOn, Employee> employee;
    public static volatile SingularAttribute<WorkOn, Products> products;

}