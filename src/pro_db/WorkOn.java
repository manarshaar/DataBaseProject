/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro_db;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Manar
 */
@Entity
@Table(name = "WORK_ON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkOn.findAll", query = "SELECT w FROM WorkOn w"),
    @NamedQuery(name = "WorkOn.findByEssn", query = "SELECT w FROM WorkOn w WHERE w.workOnPK.essn = :essn"),
    @NamedQuery(name = "WorkOn.findByPcode", query = "SELECT w FROM WorkOn w WHERE w.workOnPK.pcode = :pcode"),
    @NamedQuery(name = "WorkOn.findByHours", query = "SELECT w FROM WorkOn w WHERE w.hours = :hours")})
public class WorkOn implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkOnPK workOnPK;
    @Basic(optional = false)
    @Column(name = "HOURS")
    private BigInteger hours;
    @JoinColumn(name = "ESSN", referencedColumnName = "SSN", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;
    @JoinColumn(name = "PCODE", referencedColumnName = "PRODUCT CODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Products products;

    public WorkOn() {
    }

    public WorkOn(WorkOnPK workOnPK) {
        this.workOnPK = workOnPK;
    }

    public WorkOn(WorkOnPK workOnPK, BigInteger hours) {
        this.workOnPK = workOnPK;
        this.hours = hours;
    }

    public WorkOn(BigInteger essn, BigInteger pcode) {
        this.workOnPK = new WorkOnPK(essn, pcode);
    }

    public WorkOnPK getWorkOnPK() {
        return workOnPK;
    }

    public void setWorkOnPK(WorkOnPK workOnPK) {
        this.workOnPK = workOnPK;
    }

    public BigInteger getHours() {
        return hours;
    }

    public void setHours(BigInteger hours) {
        this.hours = hours;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workOnPK != null ? workOnPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkOn)) {
            return false;
        }
        WorkOn other = (WorkOn) object;
        if ((this.workOnPK == null && other.workOnPK != null) || (this.workOnPK != null && !this.workOnPK.equals(other.workOnPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.WorkOn[ workOnPK=" + workOnPK + " ]";
    }
    
}
