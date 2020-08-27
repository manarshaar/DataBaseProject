package pro_db;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pro_db.Employee;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T23:41:49")
@StaticMetamodel(ProcurementEngineer.class)
public class ProcurementEngineer_ { 

    public static volatile SingularAttribute<ProcurementEngineer, BigDecimal> essn;
    public static volatile SingularAttribute<ProcurementEngineer, BigInteger> yearsOfExpirement;
    public static volatile SingularAttribute<ProcurementEngineer, Employee> employee;
    public static volatile SingularAttribute<ProcurementEngineer, String> certification;

}