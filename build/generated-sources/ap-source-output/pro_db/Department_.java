package pro_db;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pro_db.Employee;
import pro_db.MainOffice;
import pro_db.ManagesDep;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T23:41:49")
@StaticMetamodel(Department.class)
public class Department_ { 

    public static volatile SingularAttribute<Department, BigDecimal> depNumber;
    public static volatile SingularAttribute<Department, BigInteger> telephoneNo;
    public static volatile SingularAttribute<Department, Date> closingTime;
    public static volatile SingularAttribute<Department, Date> openingTime;
    public static volatile SingularAttribute<Department, BigInteger> noOfEmployees;
    public static volatile CollectionAttribute<Department, Employee> employeeCollection;
    public static volatile CollectionAttribute<Department, MainOffice> mainOfficeCollection;
    public static volatile SingularAttribute<Department, String> location;
    public static volatile SingularAttribute<Department, String> depName;
    public static volatile CollectionAttribute<Department, ManagesDep> managesDepCollection;

}