/*
 * Copyright (C) 2017 anto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fileConvert;

import static amr2fred.Glossary.VN_DATA_NS;
import java.util.Objects;

/**
 *
 * @author anto
 */
public class Triple {

    private String subject;
    private String relation;
    private String object;
    private static int tNum = 0;
    
    public String getSubject() {
        return subject;
    }
    
    public String getRelation() {
        return relation;
    }
    
    public String getObject() {
        return object;
    }
    
    public Triple(String subject, String relation, String object) {
        this.subject = subject;
        this.relation = relation;
        this.object = object;
        tNum += 1;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.subject);
        hash = 67 * hash + Objects.hashCode(this.relation);
        hash = 67 * hash + Objects.hashCode(this.object);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Triple other = (Triple) obj;
        
        String thisSubject=this.subject, otherSubject=other.subject;
        
        if (!thisSubject.contains(otherSubject) && !otherSubject.contains(thisSubject)) {
            return false;
        }
        
        String thisRelation = stringAdj(this.relation), otherRelation = stringAdj(other.relation);
        
        if (!thisRelation.contains(otherRelation) && !otherRelation.contains(thisRelation)) {
            return false;
        }
        
        String thisObject = stringAdj(this.object), otherObject = stringAdj(other.object);
        
        if (!thisObject.contains(otherObject) && !otherObject.contains(thisObject)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        
        return "\n" + this.subject + " " + this.relation + " " + this.object + " .";
        
    }
    
    public static int gettNum() {
        return tNum;
    }
    
    private String stringAdj(String obj) {
        
        obj = obj.replace("vnrole.owl#", "");
        
        if (obj.contains(VN_DATA_NS)) {
            obj = obj.substring(0, obj.indexOf("_", VN_DATA_NS.length()));
        }
        
        return obj;
    }
    
}
