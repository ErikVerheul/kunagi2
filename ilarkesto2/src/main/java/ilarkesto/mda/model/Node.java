/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.mda.model;

import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.List;
import static java.util.UUID.randomUUID;

/**
 *
 * @author erik
 */
public class Node implements Comparable<Node> {

	private String id;
	private final Model model;
	private final Node parent;
	private final String type;
	private String value;
	private final List<Node> children = new ArrayList<>();
	private boolean transient_;

	Node(Model model, String id, Node parent, String type, String value) {
		super();
		this.model = model;
		this.id = id;
		this.parent = parent;
		this.type = type;
		this.value = value;

		if (this.id == null) {
                        this.id = randomUUID().toString();
                }
	}

    /**
     *
     * @return
     */
    public Model getModel() {
		return model;
	}

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public Node addChild(String type, String value) {
		return addChild(null, type, value);
	}

	Node addChild(String id, String type, String value) {
		Node child = new Node(model, id, this, type, value);
		children.add(child);
		return child;
	}

    /**
     *
     * @param type
     * @return
     */
    public boolean isType(String type) {
		return this.type.equals(type);
	}

    /**
     *
     * @param types
     * @return
     */
    public boolean isType(String... types) {
		for (String local_type : types) {
			if (isType(local_type)) {
                                return true;
                        }
		}
		return false;
	}

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public boolean containsChild(String type, String value) {
		return getChild(type, value) != null;
	}

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public boolean containsChild(String type, boolean value) {
		Node child = getChildByType(type);
		return child == null ? false : child.isValue(valueOf(value));
	}

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public Node getChildRecursive(String type, String value) {
		for (Node child : getChildrenByType(type)) {
			if (value.equals(child.getValue())) {
                                return child;
                        }
		}
		for (Node child : getChildren()) {
			Node ret = child.getChildRecursive(type, value);
			if (ret != null) {
                                return ret;
                        }
		}
		return null;
	}

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public Node getChildOrCreate(String type, String value) {
		Node child = getChild(type, value);
		if (child == null) {
                        child = addChild(type, value);
                }
		return child;
	}

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public Node getChild(String type, String value) {
		for (Node child : getChildrenByType(type)) {
			if (value.equals(child.getValue())) {
                                return child;
                        }
		}
		return null;
	}

    /**
     *
     * @param type
     * @return
     */
    public List<Node> getChildrenByType(String type) {
		List<Node> ret = new ArrayList<>();
		for (Node child : getChildren()) {
			if (child.isType(type)) {
                                ret.add(child);
                        }
		}
		return ret;
	}

    /**
     *
     * @param type
     * @return
     */
    public List<Node> getChildrenByTypeRecursive(String type) {
		List<Node> ret = new ArrayList<>();
		for (Node child : getChildren()) {
			if (child.isType(type)) {
                                ret.add(child);
                        }
			ret.addAll(child.getChildrenByTypeRecursive(type));
		}
		return ret;
	}

    /**
     *
     * @param type
     * @return
     */
    public String getChildValueByType(String type) {
		Node child = getChildByType(type);
		return child == null ? null : child.getValue();
	}

    /**
     *
     * @param type
     * @return
     */
    public Node getChildByType(String type) {
		for (Node child : getChildren()) {
			if (child.isType(type)) {
                                return child;
                        }
		}
		return null;
	}

    /**
     *
     * @param type
     * @return
     */
    public boolean containsChildByType(String type) {
		return getChildByType(type) != null;
	}

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public boolean containsChildByTypeAndValue(String type, String value) {
		Node child = getChildByType(type);
		if (child == null) {
                        return false;
                }
		return child.isValue(value);
	}

    /**
     *
     * @param type
     * @return
     */
    public boolean containsChildByTypeAndValueTrue(String type) {
		Node child = getChildByType(type);
		if (child == null) {
                        return false;
                }
		return child.isValueTrue();
	}

    /**
     *
     * @param type
     * @return
     */
    public boolean containsChildByTypeAndValueFalse(String type) {
		Node child = getChildByType(type);
		if (child == null) {
                        return false;
                }
		return child.isValueFalse();
	}

    /**
     *
     * @return
     */
    public String getLabel() {
		return getType() + ":" + getValue();
	}

    /**
     *
     * @return
     */
    public List<Node> getChildren() {
		return children;
	}

    /**
     *
     * @return
     */
    public boolean containsChildren() {
		return !getChildren().isEmpty();
	}

    /**
     *
     * @param child
     * @return
     */
    public boolean removeChild(Node child) {
		return children.remove(child);
	}

    /**
     *
     */
    public void removeAllChildren() {
		children.clear();
	}

    /**
     *
     */
    public void removeFromParent() {
		getParent().removeChild(this);
	}

    /**
     *
     * @return
     */
    public String getId() {
		return id;
	}

    /**
     *
     * @return
     */
    public Node getParent() {
		return parent;
	}

    /**
     *
     * @return
     */
    public String getParentId() {
		return parent == null ? null : parent.getId();
	}

    /**
     *
     * @return
     */
    public String getType() {
		return type;
	}

    /**
     *
     * @param value
     */
    public void setValue(String value) {
		this.value = value;
	}

    /**
     *
     * @return
     */
    public String getValue() {
		return value;
	}

    /**
     *
     * @param test
     * @return
     */
    public boolean isValue(String test) {
		if (value == null && test == null) {
                        return true;
                }
		if (value == null) {
                        return false;
                }
		if (test == null) {
                        return false;
                }
		return value.equals(test);
	}

    /**
     *
     * @return
     */
    public boolean isValueTrue() {
		return isValue("true");
	}

    /**
     *
     * @return
     */
    public boolean isValueFalse() {
		return isValue("false");
	}

    /**
     *
     * @return
     */
    public boolean isTransient() {
		return transient_;
	}

    /**
     *
     * @param transient_
     */
    public void setTransient(boolean transient_) {
		this.transient_ = transient_;
	}

	@Override
	public int compareTo(Node o) {
		int typeRet = type.compareTo(o.type);
		return typeRet == 0 ? value.compareTo(o.value) : typeRet;
	}

	@Override
	public String toString() {
		return getLabel();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Node)) {
                        return false;
                }
		return id.equals(((Node) obj).id);
	}

	// --- helper ---

    /**
     *
     * @param type
     * @return
     */
    
	public Node getSuperparentByType(String type) {
		if (parent == null) {
                        return null;
                }
		if (parent.isType(type)) {
                        return parent;
                }
		return parent.getSuperparentByType(type);
	}

}
