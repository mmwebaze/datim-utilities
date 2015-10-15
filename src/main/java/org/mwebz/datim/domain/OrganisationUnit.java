package org.mwebz.datim.domain;

/*
 * Copyright (c) 2004-2015, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Kristian Nordal
 */
//@JacksonXmlRootElement( localName = "organisationUnit", namespace = DxfNamespaces.DXF_2_0 )
public class OrganisationUnit
{

    private String uuid;

    private OrganisationUnit parent;

    //private Date openingDate;

    //private Date closedDate;

    private String comment;

    private String featureType;

    private String coordinates;

    private String url;

    private String contactPerson;

    private String address;

    private String email;

    private String phoneNumber;
    private String id;

    //private Set<OrganisationUnitGroup> groups = new HashSet<>();

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     * Set of the dynamic attributes values that belong to this
     * organisationUnit.
     */

    // -------------------------------------------------------------------------
    // Transient fields
    // -------------------------------------------------------------------------

    private Set<OrganisationUnit> children = new HashSet<>();

    private transient boolean currentParent;

    private transient int level;

    private transient String type;

    private transient List<String> groupNames = new ArrayList<>();

    private transient Double value;

	private String shortName;

	private String code;

	private String name;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public OrganisationUnit()
    {
        this.uuid = UUID.randomUUID().toString();
     //   setAutoFields();
    }

    public OrganisationUnit( String name )
    {
        this();
        this.name = name;
    }

    /**
     * @param name
     * @param shortName
     * @param openingDate
     * @param closedDate
     * @param active
     * @param comment
     */
    public OrganisationUnit( String name, String shortName, String code, Date openingDate, Date closedDate,
        String comment )
    {
        this( name );
        this.shortName = shortName;
        this.code = code;
        //this.openingDate = openingDate;
        //this.closedDate = closedDate;
        this.comment = comment;
    }

    /**
     * @param name
     * @param parent
     * @param shortName
     * @param openingDate
     * @param closedDate
     * @param active
     * @param comment
     */
    public OrganisationUnit( String name, OrganisationUnit parent, String shortName, String code, Date openingDate,
        Date closedDate, String comment )
    {
        this( name );
        this.parent = parent;
        this.shortName = shortName;
        this.code = code;
       // this.openingDate = openingDate;
        //this.closedDate = closedDate;
        this.comment = comment;
    }
    
    public Set<OrganisationUnit> getGrandChildren()
    {
        Set<OrganisationUnit> grandChildren = new HashSet<>();

        for ( OrganisationUnit child : children )
        {
            grandChildren.addAll( child.getChildren() );
        }

        return grandChildren;
    }


    public boolean hasChild()
    {
        return !this.children.isEmpty();
    }

    public boolean isLeaf()
    {
        return children == null || children.isEmpty();
    }

    public boolean hasChildrenWithCoordinates()
    {
        for ( OrganisationUnit child : children )
        {
            if ( child.hasCoordinates() )
            {
                return true;
            }
        }

        return false;
    }

    public boolean isDescendant( OrganisationUnit ancestor )
    {
        if ( ancestor == null )
        {
            return false;
        }

        OrganisationUnit unit = this;

        while ( unit != null )
        {
            if ( ancestor.equals( unit ) )
            {
                return true;
            }

            unit = unit.getParent();
        }

        return false;
    }

    public boolean isDescendant( Set<OrganisationUnit> ancestors )
    {
        if ( ancestors == null || ancestors.isEmpty() )
        {
            return false;
        }

        OrganisationUnit unit = this;

        while ( unit != null )
        {
            if ( ancestors.contains( unit ) )
            {
                return true;
            }

            unit = unit.getParent();
        }

        return false;
    }

    public boolean hasCoordinatesUp()
    {
        if ( parent != null )
        {
            if ( parent.getParent() != null )
            {
                return parent.getParent().hasChildrenWithCoordinates();
            }
        }

        return false;
    }

    public boolean hasCoordinates()
    {
        return coordinates != null && coordinates.trim().length() > 0;
    }

    public String getValidCoordinates()
    {
        return coordinates != null && !coordinates.isEmpty() ? coordinates : "[]";
    }

    /**
     * Returns the list of ancestor organisation units for this organisation unit.
     * Does not include itself. The list is ordered by root first.
     */
    public List<OrganisationUnit> getAncestors()
    {
        List<OrganisationUnit> units = new ArrayList<>();

        OrganisationUnit unit = parent;

        while ( unit != null )
        {
            units.add( unit );
            unit = unit.getParent();
        }

        Collections.reverse( units );
        return units;
    }

    /**
     * Returns the list of ancestor organisation units up the any of the given roots
     * for this organisation unit. Does not include itself. The list is ordered
     * by root first.
     *
     * @param roots the root organisation units, if null using real roots.
     */
    public List<OrganisationUnit> getAncestors( Collection<OrganisationUnit> roots )
    {
        List<OrganisationUnit> units = new ArrayList<>();

        OrganisationUnit unit = parent;

        while ( unit != null )
        {
            units.add( unit );

            if ( roots != null && roots.contains( unit ) )
            {
                break;
            }

            unit = unit.getParent();
        }

        Collections.reverse( units );
        return units;
    }

    public void updateParent( OrganisationUnit newParent )
    {
        if ( this.parent != null && this.parent.getChildren() != null )
        {
            this.parent.getChildren().remove( this );
        }

        this.parent = newParent;

        newParent.getChildren().add( this );
    }

    public Set<OrganisationUnit> getChildrenThisIfEmpty()
    {
        Set<OrganisationUnit> set = new HashSet<>();

        if ( hasChild() )
        {
            set = children;
        }
        else
        {
            set.add( this );
        }

        return set;
    }

    public int getOrganisationUnitLevel()
    {
        int currentLevel = 1;

        OrganisationUnit thisParent = this.parent;

        while ( thisParent != null )
        {
            ++currentLevel;

            thisParent = thisParent.getParent();
        }

        this.level = currentLevel;

        return currentLevel;
    }


    public boolean hasLevel()
    {
        return level > 0;
    }


    public boolean isRoot()
    {
        return parent == null;
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------


    public String getUuid()
    {
        return uuid;
    }

    public void setUuid( String uuid )
    {
        this.uuid = uuid;
    }


    public OrganisationUnit getParent()
    {
        return parent;
    }

    public void setParent( OrganisationUnit parent )
    {
        this.parent = parent;
    }


    public Set<OrganisationUnit> getChildren()
    {
        return children;
    }

    public void setChildren( Set<OrganisationUnit> children )
    {
        this.children = children;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public String getFeatureType()
    {
        return featureType;
    }

    public void setFeatureType( String featureType )
    {
        this.featureType = featureType;
    }

    public String getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates( String coordinates )
    {
        this.coordinates = coordinates;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getContactPerson()
    {
        return contactPerson;
    }

    public void setContactPerson( String contactPerson )
    {
        this.contactPerson = contactPerson;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber )
    {
        this.phoneNumber = phoneNumber;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

  /*  public Set<OrganisationUnitGroup> getGroups()
    {
        return groups;
    }

    public void setGroups( Set<OrganisationUnitGroup> groups )
    {
        this.groups = groups;
    }*/

    // -------------------------------------------------------------------------
    // Getters and setters for transient fields
    // -------------------------------------------------------------------------

    public int getLevel()
    {
        return level;
    }

    public void setLevel( int level )
    {
        this.level = level;
    }

    public List<String> getGroupNames()
    {
        return groupNames;
    }

    public void setGroupNames( List<String> groupNames )
    {
        this.groupNames = groupNames;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue( Double value )
    {
        this.value = value;
    }

    public boolean isCurrentParent()
    {
        return currentParent;
    }

    public void setCurrentParent( boolean currentParent )
    {
        this.currentParent = currentParent;
    }

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "OrganisationUnit [uuid=" + uuid + ", parent=" + parent
				+ ", comment=" + comment + ", featureType=" + featureType
				+ ", coordinates=" + coordinates + ", url=" + url
				+ ", contactPerson=" + contactPerson + ", address=" + address
				+ ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", children=" + children + ", shortName=" + shortName
				+ ", code=" + code + ", name=" + name + "]";
	}

}
