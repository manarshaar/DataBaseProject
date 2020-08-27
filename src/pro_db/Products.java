/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro_db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Manar
 */
@Entity
@Table(name = "PRODUCTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name"),
    @NamedQuery(name = "Products.findByProductCode", query = "SELECT p FROM Products p WHERE p.productCode = :productCode"),
    @NamedQuery(name = "Products.findByCost", query = "SELECT p FROM Products p WHERE p.cost = :cost"),
    @NamedQuery(name = "Products.findByQuantity", query = "SELECT p FROM Products p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "Products.findByWaranty", query = "SELECT p FROM Products p WHERE p.waranty = :waranty"),
    @NamedQuery(name = "Products.findByManufacturer", query = "SELECT p FROM Products p WHERE p.manufacturer = :manufacturer"),
    @NamedQuery(name = "Products.findByCountryOfOrigin", query = "SELECT p FROM Products p WHERE p.countryOfOrigin = :countryOfOrigin")})
public class Products implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "NAME")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PRODUCT CODE")
    private BigDecimal productCode;
    @Column(name = "COST")
    private BigInteger cost;
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Column(name = "WARANTY")
    private BigInteger waranty;
    @Column(name = "MANUFACTURER")
    private String manufacturer;
    @Column(name = "COUNTRY OF ORIGIN")
    private String countryOfOrigin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private Collection<WorkOn> workOnCollection;

    public Products() {
    }

    public Products(BigDecimal productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getProductCode() {
        return productCode;
    }

    public void setProductCode(BigDecimal productCode) {
        this.productCode = productCode;
    }

    public BigInteger getCost() {
        return cost;
    }

    public void setCost(BigInteger cost) {
        this.cost = cost;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigInteger getWaranty() {
        return waranty;
    }

    public void setWaranty(BigInteger waranty) {
        this.waranty = waranty;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @XmlTransient
    public Collection<WorkOn> getWorkOnCollection() {
        return workOnCollection;
    }

    public void setWorkOnCollection(Collection<WorkOn> workOnCollection) {
        this.workOnCollection = workOnCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productCode != null ? productCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productCode == null && other.productCode != null) || (this.productCode != null && !this.productCode.equals(other.productCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.Products[ productCode=" + productCode + " ]";
    }
    
}
