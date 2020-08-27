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
public class WorkOnPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ESSN")
    private BigInteger essn;
    @Basic(optional = false)
    @Column(name = "PCODE")
    private BigInteger pcode;

    public WorkOnPK() {
    }

    public WorkOnPK(BigInteger essn, BigInteger pcode) {
        this.essn = essn;
        this.pcode = pcode;
    }

    public BigInteger getEssn() {
        return essn;
    }

    public void setEssn(BigInteger essn) {
        this.essn = essn;
    }

    public BigInteger getPcode() {
        return pcode;
    }

    public void setPcode(BigInteger pcode) {
        this.pcode = pcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (essn != null ? essn.hashCode() : 0);
        hash += (pcode != null ? pcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkOnPK)) {
            return false;
        }
        WorkOnPK other = (WorkOnPK) object;
        if ((this.essn == null && other.essn != null) || (this.essn != null && !this.essn.equals(other.essn))) {
            return false;
        }
        if ((this.pcode == null && other.pcode != null) || (this.pcode != null && !this.pcode.equals(other.pcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.WorkOnPK[ essn=" + essn + ", pcode=" + pcode + " ]";
    }
    
}
