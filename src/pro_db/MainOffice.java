/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro_db;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "MAIN_OFFICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MainOffice.findAll", query = "SELECT m FROM MainOffice m"),
    @NamedQuery(name = "MainOffice.findByFaxNo", query = "SELECT m FROM MainOffice m WHERE m.mainOfficePK.faxNo = :faxNo"),
    @NamedQuery(name = "MainOffice.findByDno", query = "SELECT m FROM MainOffice m WHERE m.mainOfficePK.dno = :dno")})
public class MainOffice implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MainOfficePK mainOfficePK;
    @JoinColumn(name = "DNO", referencedColumnName = "DEP NUMBER", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Department department;

    public MainOffice() {
    }

    public MainOffice(MainOfficePK mainOfficePK) {
        this.mainOfficePK = mainOfficePK;
    }

    public MainOffice(BigInteger faxNo, BigInteger dno) {
        this.mainOfficePK = new MainOfficePK(faxNo, dno);
    }

    public MainOfficePK getMainOfficePK() {
        return mainOfficePK;
    }

    public void setMainOfficePK(MainOfficePK mainOfficePK) {
        this.mainOfficePK = mainOfficePK;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mainOfficePK != null ? mainOfficePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MainOffice)) {
            return false;
        }
        MainOffice other = (MainOffice) object;
        if ((this.mainOfficePK == null && other.mainOfficePK != null) || (this.mainOfficePK != null && !this.mainOfficePK.equals(other.mainOfficePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.MainOffice[ mainOfficePK=" + mainOfficePK + " ]";
    }
    
}
