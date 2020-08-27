/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro_db;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Manar
 */
@Entity
@Table(name = "MANAGES_DEP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ManagesDep.findAll", query = "SELECT m FROM ManagesDep m"),
    @NamedQuery(name = "ManagesDep.findByEssn", query = "SELECT m FROM ManagesDep m WHERE m.managesDepPK.essn = :essn"),
    @NamedQuery(name = "ManagesDep.findByDepNo", query = "SELECT m FROM ManagesDep m WHERE m.managesDepPK.depNo = :depNo"),
    @NamedQuery(name = "ManagesDep.findByStartDate", query = "SELECT m FROM ManagesDep m WHERE m.startDate = :startDate")})
public class ManagesDep implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ManagesDepPK managesDepPK;
    @Column(name = "START DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @JoinColumn(name = "DEP NO", referencedColumnName = "DEP NUMBER", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Department department;
    @JoinColumn(name = "ESSN", referencedColumnName = "SSN", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;

    public ManagesDep() {
    }

    public ManagesDep(ManagesDepPK managesDepPK) {
        this.managesDepPK = managesDepPK;
    }

    public ManagesDep(BigInteger essn, BigInteger depNo) {
        this.managesDepPK = new ManagesDepPK(essn, depNo);
    }

    public ManagesDepPK getManagesDepPK() {
        return managesDepPK;
    }

    public void setManagesDepPK(ManagesDepPK managesDepPK) {
        this.managesDepPK = managesDepPK;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (managesDepPK != null ? managesDepPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManagesDep)) {
            return false;
        }
        ManagesDep other = (ManagesDep) object;
        if ((this.managesDepPK == null && other.managesDepPK != null) || (this.managesDepPK != null && !this.managesDepPK.equals(other.managesDepPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.ManagesDep[ managesDepPK=" + managesDepPK + " ]";
    }
    
}
