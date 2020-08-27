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
import javax.persistence.Embeddable;

/**
 *
 * @author Manar
 */
@Embeddable
public class MainOfficePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "FAX NO")
    private BigInteger faxNo;
    @Basic(optional = false)
    @Column(name = "DNO")
    private BigInteger dno;

    public MainOfficePK() {
    }

    public MainOfficePK(BigInteger faxNo, BigInteger dno) {
        this.faxNo = faxNo;
        this.dno = dno;
    }

    public BigInteger getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(BigInteger faxNo) {
        this.faxNo = faxNo;
    }

    public BigInteger getDno() {
        return dno;
    }

    public void setDno(BigInteger dno) {
        this.dno = dno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faxNo != null ? faxNo.hashCode() : 0);
        hash += (dno != null ? dno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MainOfficePK)) {
            return false;
        }
        MainOfficePK other = (MainOfficePK) object;
        if ((this.faxNo == null && other.faxNo != null) || (this.faxNo != null && !this.faxNo.equals(other.faxNo))) {
            return false;
        }
        if ((this.dno == null && other.dno != null) || (this.dno != null && !this.dno.equals(other.dno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.MainOfficePK[ faxNo=" + faxNo + ", dno=" + dno + " ]";
    }
    
}
