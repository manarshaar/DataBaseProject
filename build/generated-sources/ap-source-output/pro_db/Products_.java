package pro_db;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pro_db.WorkOn;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T23:41:49")
@StaticMetamodel(Products.class)
public class Products_ { 

    public static volatile SingularAttribute<Products, BigDecimal> productCode;
    public static volatile SingularAttribute<Products, BigInteger> cost;
    public static volatile SingularAttribute<Products, BigInteger> quantity;
    public static volatile SingularAttribute<Products, BigInteger> waranty;
    public static volatile SingularAttribute<Products, String> name;
    public static volatile CollectionAttribute<Products, WorkOn> workOnCollection;
    public static volatile SingularAttribute<Products, String> countryOfOrigin;
    public static volatile SingularAttribute<Products, String> manufacturer;

}