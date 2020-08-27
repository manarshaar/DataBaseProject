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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "EMPLOYEE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findBySsn", query = "SELECT e FROM Employee e WHERE e.ssn = :ssn"),
    @NamedQuery(name = "Employee.findByFname", query = "SELECT e FROM Employee e WHERE e.fname = :fname"),
    @NamedQuery(name = "Employee.findBySname", query = "SELECT e FROM Employee e WHERE e.sname = :sname"),
    @NamedQuery(name = "Employee.findByLname", query = "SELECT e FROM Employee e WHERE e.lname = :lname"),
    @NamedQuery(name = "Employee.findByAddress", query = "SELECT e FROM Employee e WHERE e.address = :address"),
    @NamedQuery(name = "Employee.findByBirthDate", query = "SELECT e FROM Employee e WHERE e.birthDate = :birthDate"),
    @NamedQuery(name = "Employee.findByPhoneNo", query = "SELECT e FROM Employee e WHERE e.phoneNo = :phoneNo")})
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SSN")
    private BigDecimal ssn;
    @Basic(optional = false)
    @Column(name = "FNAME")
    private String fname;
    @Column(name = "SNAME")
    private String sname;
    @Column(name = "LNAME")
    private String lname;
    @Basic(optional = false)
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "BIRTH DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @Column(name = "PHONE NO")
    private BigInteger phoneNo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Collection<ManagesDep> managesDepCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private ProcurementEngineer procurementEngineer;
    @JoinColumn(name = "DEP NO", referencedColumnName = "DEP NUMBER")
    @ManyToOne
    private Department depNo;
    @JoinColumns({
        @JoinColumn(name = "EXNAME", referencedColumnName = "NAME"),
        @JoinColumn(name = "EXDATE", referencedColumnName = "DATE")})
    @ManyToOne
    private InternationalExhibtion internationalExhibtion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Collection<WorkOn> workOnCollection;

    public Employee() {
    }

    public Employee(BigDecimal ssn) {
        this.ssn = ssn;
    }

    public Employee(BigDecimal ssn, String fname, String address) {
        this.ssn = ssn;
        this.fname = fname;
        this.address = address;
    }

    public BigDecimal getSsn() {
        return ssn;
    }

    public void setSsn(BigDecimal ssn) {
        this.ssn = ssn;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public BigInteger getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(BigInteger phoneNo) {
        this.phoneNo = phoneNo;
    }

    @XmlTransient
    public Collection<ManagesDep> getManagesDepCollection() {
        return managesDepCollection;
    }

    public void setManagesDepCollection(Collection<ManagesDep> managesDepCollection) {
        this.managesDepCollection = managesDepCollection;
    }

    public ProcurementEngineer getProcurementEngineer() {
        return procurementEngineer;
    }

    public void setProcurementEngineer(ProcurementEngineer procurementEngineer) {
        this.procurementEngineer = procurementEngineer;
    }

    public Department getDepNo() {
        return depNo;
    }

    public void setDepNo(Department depNo) {
        this.depNo = depNo;
    }

    public InternationalExhibtion getInternationalExhibtion() {
        return internationalExhibtion;
    }

    public void setInternationalExhibtion(InternationalExhibtion internationalExhibtion) {
        this.internationalExhibtion = internationalExhibtion;
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
        hash += (ssn != null ? ssn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.ssn == null && other.ssn != null) || (this.ssn != null && !this.ssn.equals(other.ssn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.Employee[ ssn=" + ssn + " ]";
    }
    
}
