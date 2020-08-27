/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro_db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Manar
 */
@Entity
@Table(name = "PROCUREMENT_ENGINEER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcurementEngineer.findAll", query = "SELECT p FROM ProcurementEngineer p"),
    @NamedQuery(name = "ProcurementEngineer.findByEssn", query = "SELECT p FROM ProcurementEngineer p WHERE p.essn = :essn"),
    @NamedQuery(name = "ProcurementEngineer.findByCertification", query = "SELECT p FROM ProcurementEngineer p WHERE p.certification = :certification"),
    @NamedQuery(name = "ProcurementEngineer.findByYearsOfExpirement", query = "SELECT p FROM ProcurementEngineer p WHERE p.yearsOfExpirement = :yearsOfExpirement")})
public class ProcurementEngineer implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ESSN")
    private BigDecimal essn;
    @Column(name = "CERTIFICATION")
    private String certification;
    @Column(name = "YEARS OF EXPIREMENT")
    private BigInteger yearsOfExpirement;
    @JoinColumn(name = "ESSN", referencedColumnName = "SSN", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Employee employee;

    public ProcurementEngineer() {
    }

    public ProcurementEngineer(BigDecimal essn) {
        this.essn = essn;
    }

    public BigDecimal getEssn() {
        return essn;
    }

    public void setEssn(BigDecimal essn) {
        this.essn = essn;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public BigInteger getYearsOfExpirement() {
        return yearsOfExpirement;
    }

    public void setYearsOfExpirement(BigInteger yearsOfExpirement) {
        this.yearsOfExpirement = yearsOfExpirement;
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
        hash += (essn != null ? essn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcurementEngineer)) {
            return false;
        }
        ProcurementEngineer other = (ProcurementEngineer) object;
        if ((this.essn == null && other.essn != null) || (this.essn != null && !this.essn.equals(other.essn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.ProcurementEngineer[ essn=" + essn + " ]";
    }
    
}
