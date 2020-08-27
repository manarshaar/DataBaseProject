/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro_db;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "INTERNATIONAL_EXHIBTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InternationalExhibtion.findAll", query = "SELECT i FROM InternationalExhibtion i"),
    @NamedQuery(name = "InternationalExhibtion.findByName", query = "SELECT i FROM InternationalExhibtion i WHERE i.internationalExhibtionPK.name = :name"),
    @NamedQuery(name = "InternationalExhibtion.findByCountry", query = "SELECT i FROM InternationalExhibtion i WHERE i.country = :country"),
    @NamedQuery(name = "InternationalExhibtion.findByCostOfTravel", query = "SELECT i FROM InternationalExhibtion i WHERE i.costOfTravel = :costOfTravel"),
    @NamedQuery(name = "InternationalExhibtion.findByDate", query = "SELECT i FROM InternationalExhibtion i WHERE i.internationalExhibtionPK.date = :date"),
    @NamedQuery(name = "InternationalExhibtion.findByEmployeeTravelling", query = "SELECT i FROM InternationalExhibtion i WHERE i.employeeTravelling = :employeeTravelling")})
public class InternationalExhibtion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InternationalExhibtionPK internationalExhibtionPK;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "COST OF TRAVEL($)")
    private BigInteger costOfTravel;
    @Column(name = "EMPLOYEE TRAVELLING")
    private String employeeTravelling;
    @OneToMany(mappedBy = "internationalExhibtion")
    private Collection<Employee> employeeCollection;

    public InternationalExhibtion() {
    }

    public InternationalExhibtion(InternationalExhibtionPK internationalExhibtionPK) {
        this.internationalExhibtionPK = internationalExhibtionPK;
    }

    public InternationalExhibtion(String name, Date date) {
        this.internationalExhibtionPK = new InternationalExhibtionPK(name, date);
    }

    public InternationalExhibtionPK getInternationalExhibtionPK() {
        return internationalExhibtionPK;
    }

    public void setInternationalExhibtionPK(InternationalExhibtionPK internationalExhibtionPK) {
        this.internationalExhibtionPK = internationalExhibtionPK;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigInteger getCostOfTravel() {
        return costOfTravel;
    }

    public void setCostOfTravel(BigInteger costOfTravel) {
        this.costOfTravel = costOfTravel;
    }

    public String getEmployeeTravelling() {
        return employeeTravelling;
    }

    public void setEmployeeTravelling(String employeeTravelling) {
        this.employeeTravelling = employeeTravelling;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (internationalExhibtionPK != null ? internationalExhibtionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InternationalExhibtion)) {
            return false;
        }
        InternationalExhibtion other = (InternationalExhibtion) object;
        if ((this.internationalExhibtionPK == null && other.internationalExhibtionPK != null) || (this.internationalExhibtionPK != null && !this.internationalExhibtionPK.equals(other.internationalExhibtionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.InternationalExhibtion[ internationalExhibtionPK=" + internationalExhibtionPK + " ]";
    }
    
}
