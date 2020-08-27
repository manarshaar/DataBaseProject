package pro_db;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pro_db.Department;
import pro_db.InternationalExhibtion;
import pro_db.ManagesDep;
import pro_db.ProcurementEngineer;
import pro_db.WorkOn;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T23:41:49")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, Department> depNo;
    public static volatile SingularAttribute<Employee, String> fname;
    public static volatile SingularAttribute<Employee, InternationalExhibtion> internationalExhibtion;
    public static volatile SingularAttribute<Employee, String> lname;
    public static volatile SingularAttribute<Employee, String> address;
    public static volatile SingularAttribute<Employee, ProcurementEngineer> procurementEngineer;
    public static volatile SingularAttribute<Employee, String> sname;
    public static volatile CollectionAttribute<Employee, WorkOn> workOnCollection;
    public static volatile SingularAttribute<Employee, Date> birthDate;
    public static volatile SingularAttribute<Employee, BigInteger> phoneNo;
    public static volatile SingularAttribute<Employee, BigDecimal> ssn;
    public static volatile CollectionAttribute<Employee, ManagesDep> managesDepCollection;

}