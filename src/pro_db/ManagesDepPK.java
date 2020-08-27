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
public class ManagesDepPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ESSN")
    private BigInteger essn;
    @Basic(optional = false)
    @Column(name = "DEP NO")
    private BigInteger depNo;

    public ManagesDepPK() {
    }

    public ManagesDepPK(BigInteger essn, BigInteger depNo) {
        this.essn = essn;
        this.depNo = depNo;
    }

    public BigInteger getEssn() {
        return essn;
    }

    public void setEssn(BigInteger essn) {
        this.essn = essn;
    }

    public BigInteger getDepNo() {
        return depNo;
    }

    public void setDepNo(BigInteger depNo) {
        this.depNo = depNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (essn != null ? essn.hashCode() : 0);
        hash += (depNo != null ? depNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManagesDepPK)) {
            return false;
        }
        ManagesDepPK other = (ManagesDepPK) object;
        if ((this.essn == null && other.essn != null) || (this.essn != null && !this.essn.equals(other.essn))) {
            return false;
        }
        if ((this.depNo == null && other.depNo != null) || (this.depNo != null && !this.depNo.equals(other.depNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pro_db.ManagesDepPK[ essn=" + essn + ", depNo=" + depNo + " ]";
    }
    
}
