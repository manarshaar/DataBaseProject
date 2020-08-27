package pro_db;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pro_db.Employee;
import pro_db.InternationalExhibtionPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T23:41:49")
@StaticMetamodel(InternationalExhibtion.class)
public class InternationalExhibtion_ { 

    public static volatile SingularAttribute<InternationalExhibtion, String> employeeTravelling;
    public static volatile SingularAttribute<InternationalExhibtion, String> country;
    public static volatile CollectionAttribute<InternationalExhibtion, Employee> employeeCollection;
    public static volatile SingularAttribute<InternationalExhibtion, BigInteger> costOfTravel;
    public static volatile SingularAttribute<InternationalExhibtion, InternationalExhibtionPK> internationalExhibtionPK;

}