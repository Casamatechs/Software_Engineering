/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginSystem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author niraj
 */
@Entity
@Table(name = "rs_pending", catalog = "housing", schema = "")
@NamedQueries({
    @NamedQuery(name = "RsPending.findAll", query = "SELECT r FROM RsPending r")
    , @NamedQuery(name = "RsPending.findById", query = "SELECT r FROM RsPending r WHERE r.id = :id")
    , @NamedQuery(name = "RsPending.findByOption1", query = "SELECT r FROM RsPending r WHERE r.option1 = :option1")
    , @NamedQuery(name = "RsPending.findByOption2", query = "SELECT r FROM RsPending r WHERE r.option2 = :option2")
    , @NamedQuery(name = "RsPending.findByOption3", query = "SELECT r FROM RsPending r WHERE r.option3 = :option3")
    , @NamedQuery(name = "RsPending.findByOption4", query = "SELECT r FROM RsPending r WHERE r.option4 = :option4")
    , @NamedQuery(name = "RsPending.findByOption5", query = "SELECT r FROM RsPending r WHERE r.option5 = :option5")})
public class RsPending implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Option1")
    private String option1;
    @Column(name = "Option2")
    private String option2;
    @Column(name = "Option3")
    private String option3;
    @Column(name = "Option4")
    private String option4;
    @Column(name = "Option5")
    private String option5;

    public RsPending() {
    }

    public RsPending(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        String oldOption1 = this.option1;
        this.option1 = option1;
        changeSupport.firePropertyChange("option1", oldOption1, option1);
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        String oldOption2 = this.option2;
        this.option2 = option2;
        changeSupport.firePropertyChange("option2", oldOption2, option2);
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        String oldOption3 = this.option3;
        this.option3 = option3;
        changeSupport.firePropertyChange("option3", oldOption3, option3);
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        String oldOption4 = this.option4;
        this.option4 = option4;
        changeSupport.firePropertyChange("option4", oldOption4, option4);
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        String oldOption5 = this.option5;
        this.option5 = option5;
        changeSupport.firePropertyChange("option5", oldOption5, option5);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RsPending)) {
            return false;
        }
        RsPending other = (RsPending) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LoginSystem.RsPending[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
