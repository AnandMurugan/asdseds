/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Alex
 */
@Entity
public class UserAccount implements UserAccountDTO, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userName;
    private String password;
    private String fullName;
    private int userRole;
    private boolean banned;

    public UserAccount(String userName, String password, String fullName, int userRole) {
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
        this.fullName = fullName;
        banned = false;
    }
    
    public UserAccount() {
        
    }

    
    public String getUserName() {
        return userName;
    }
    
    public void setBanned(boolean banned) {
        this.banned = banned;
    }
    
    @Override
    public boolean isBanned() {
        return banned;
    }
    
    @Override
    public int getRole() {
        return userRole;
    }
    
    @Override
    public String getName() {
        return fullName;
    }
    
    @Override
    public boolean checkPassword(String password) {
        if(this.password.equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userName != null ? userName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.userName == null && other.userName != null) || (this.userName != null && !this.userName.equals(other.userName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "login.model.UserAccount[ id=" + userName + " ]";
    }
    
}
