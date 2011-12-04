/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Alex
 */
@Entity
public class User_details implements Serializable {
   
    @Id
    private String luserName;
    private String lpassword;
    private String lusergroup;
    private boolean banned;
    private String fullName;
    
    public User_details() {
        
    }

    public User_details(String userName, String password, String fullName, String group, boolean banned) throws NoSuchAlgorithmException {
        this.luserName = userName;
        this.lusergroup = group;
        this.fullName = fullName;
        this.banned = banned;
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(), 0, password.length());
        this.lpassword = new String(md.digest());
        
    }
    
    public String getLuserName() {
        return luserName;
    }

    public void setLuserName(String userName) {
        this.luserName = userName;
    }
    
    public void setLpassword(String password) {
        this.lpassword = password;
    }
    
    public String getLgroup() {
        return lusergroup;
    }
    
    public void setLGroup(String group) {
        this.lusergroup = group;
    }
    
    public boolean getBanned() {
        return banned;
    }
    
    public void setBanned(boolean banned) {
        this.banned = banned;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (luserName != null ? luserName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User_details)) {
            return false;
        }
        User_details other = (User_details) object;
        if ((this.luserName == null && other.luserName != null) || (this.luserName != null && !this.luserName.equals(other.luserName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "user.model.User_details[ id=" + luserName + " ]";
    }
    
}
