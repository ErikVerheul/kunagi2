/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.admin;

import ilarkesto.core.base.Str;
import ilarkesto.core.base.Utl;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.editor.AFieldModel;
import ilarkesto.gwt.client.editor.ATextEditorModel;
import java.util.Comparator;
import java.util.Map;
import scrum.client.ScrumGwt;
import scrum.client.ScrumScopeManager;
import scrum.client.collaboration.UsersStatus;
import scrum.client.common.LabelSupport;

public class User extends GUser implements LabelSupport, Comparable<User> {

    public static final String INITIAL_NAME = "newuser";

    public User() {
        setName(getNextNewUserName());
    }

    public User(Map data) {
        super(data);
    }

    @Override
    public String getLabel() {
        return getName();
    }

    public String getNameAndFullName() {
        String fullName = getFullName();
        if (Str.isBlank(fullName)) {
            return getName();
        }
        return getName() + " (" + fullName + ")";
    }

    private String getNextNewUserName() {
        int index = 1;
        while (true) {
            String name = "newuser" + index;
            if (getDao().getUserByName(name) == null) {
                return name;
            }
            index++;
        }
    }

    public ProjectUserConfig getProjectConfig() {
        return ScrumScopeManager.getProject().getUserConfig(this);
    }

    @Override
    public int compareTo(User u) {
        return getName().compareTo(u.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        User u = (User) o;
        return getName().equals(u.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (getName() != null ? getName().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public EmailModel getEmailModel() {
        return new EmailModel() {

            @Override
            public void setValue(String value) {
                if (!Str.isEmail(value)) {
                    throw new RuntimeException("Invalid email.");
                }
                super.setValue(value);
            }
        };
    }

    public ATextEditorModel getNameFullNameModel() {
        return new ATextEditorModel() {

            @Override
            public String getValue() {
                return getNameAndFullName();
            }

            @Override
            public void setValue(String value) {
            }
        };
    }
    public static final Comparator<User> LAST_LOGIN_COMPARATOR = new Comparator<User>() {

        @Override
        public int compare(User a, User b) {
            return Utl.compare(b.getLastLoginDateAndTime(), a.getLastLoginDateAndTime());
        }
    };
    public static final Comparator<User> NAME_COMPARATOR = new Comparator<User>() {

        @Override
        public int compare(User a, User b) {
            return a.getName().toLowerCase().compareTo(b.getName().toLowerCase());
        }
    };
    public transient static final Comparator<User> ONLINE_OFFLINE_COMPARATOR = new Comparator<User>() {

        @Override
        public int compare(User a, User b) {
            UsersStatus usersStatus = Scope.get().getComponent(UsersStatus.class);
            boolean aOnline = usersStatus.isOnline(a);
            boolean bOnline = usersStatus.isOnline(b);
            if (aOnline == bOnline) {
                return a.getName().compareTo(b.getName());
            }
            if (aOnline) {
                return -1;
            }
            return 1;
        }
    };
    private AFieldModel<String> lastLoginAgoModel;

    public AFieldModel<String> getLastLoginAgoModel() {
        if (lastLoginAgoModel == null) {
            lastLoginAgoModel = new AFieldModel<String>() {

                @Override
                public String getValue() {
                    return ScrumGwt.getPeriodToAsShortestString("login ", getLastLoginDateAndTime(), " ago");
                }
            };
        }
        return lastLoginAgoModel;
    }
}
