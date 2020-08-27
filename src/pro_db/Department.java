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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Manar
 */
@Entity
@Table(name = "DEPARTMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d"),
    @NamedQuery(name = "Department.findByDepName", query = "SELECT d FROM Department d WHERE d.depName = :depName"),
    @NamedQuery(name = "Department.findByTelephoneNo", query = "SELECT d FROM Department d WHERE d.telephoneNo = :telephoneNo"),
    @NamedQuery(name = "Department.findByDepNumber", query = "SELECT d FROM Department d WHERE d.depNumber = :depNumber"),
    @NamedQuery(name = "Department.findByLocation", query = "SELECT d FROM Department d WHERE d.location = :location"),
    @NamedQuery(name = "Department.findByOpeningTime", query = "SELECT d FROM Department d WHERE d.openingTime = :openingTime"),
    @NamedQuery(name = "Department.findByClosingTime", query = "SELECT d FROM Department d WHERE d.closingTime = :closingTime"),
    @NamedQuery(name = "Department.findByNoOfEmployees", query = "SELECT d FROM Department d WHERE d.noOfEmployees = :noOfEmployees")})
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "DEP NAME")
    private String depName;
    @Basic(optional = false)
    @Column(name = "TELEPHONE NO")
    private BigInteger telephoneNo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "DEP NUMBER")
    private BigDecimal depNumber;
    @Basic(optional = false)
    @Column(name = "LOCATION")
    private String location;
    @Basic(optional = false)
    @Column(name = "OPENING TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openingTime;
    @Basic(optional = false)
    @Column(name = "CLOSING TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closingTime;
    @Column(name = "NO OF EMPLOYEES")
    private BigInteger noOfEmployees;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private Collection<ManagesDep> managesDepCollection;
    @OneToMany(mappedBy = "depNo")
    private Collection<Employee> employeeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private Collection<MainOffice> mainOfficeCollection;

    public Department() {
    }

    public Department(BigDecimal depNumber) {
        this.depNumber = depNumber;
    }

    public Department(BigDecimal depNumber, String depName, BigInteger telephoneNo, String location, Date openingTime, Date closingTime) {
        this.depNumber = depNumber;
        this.depName = depName;
        this.telephoneNo = telephoneNo;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public BigInteger getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(BigInteger telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public BigDecimal getDepNumber() {
        return depNumber;
    }

    public void setDepNumber(BigDecimal depNumber) {
        this.depNumber = depNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public BigInteger getNoOfEmployees() {
        return noOfEmployees;
    }

    public void setNoOfEmployees(BigInteger noOfEmployees) {
        this.noOfEmployees = noOfEmployees;
    }

    @XmlTransient
    public Collection<ManagesDep> getManagesDepCollection() {
        return managesDepCollection;
    }

    public void setManagesDepCollection(Collection<ManagesDep> managesDepCollection) {
        this.managesDepCollection = managesDepCollection;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @XmlTransient
    public Collection<MainOffice> getMainOfficeCollection() {
        return mainOfficeCollection;
    }

    public void setMainOfficeCollection(Collection<MainOffice> mainOfficeCollection) {
        this.mainOfficeCollection = mainOfficeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depNumber != null ? depNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.depNumber == null && other.depNumber != null) || (this.depNumber != null && !this.depNumber.equals(other.depNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.Department[ depNumber=" + depNumber + " ]";
    }
    
}
