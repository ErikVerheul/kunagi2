// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.risks;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GRisk
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public abstract boolean isPriorityEditable();

    public GRisk() {
    }

    public GRisk(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "risk";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- project ---

    private String projectId;

    public final scrum.client.project.Project getProject() {
        if (projectId == null) return null;
        return getDao().getProject(this.projectId);
    }

    public final boolean isProjectSet() {
        return projectId != null;
    }

    public final Risk setProject(scrum.client.project.Project project) {
        String id = project == null ? null : project.getId();
        if (equals(this.projectId, id)) return (Risk) this;
        this.projectId = id;
        propertyChanged("projectId", this.projectId);
        return (Risk)this;
    }

    public final boolean isProject(scrum.client.project.Project project) {
        return equals(this.projectId, project);
    }

    // --- number ---

    private int number ;

    public final int getNumber() {
        return this.number ;
    }

    public final Risk setNumber(int number) {
        if (isNumber(number)) return (Risk)this;
        this.number = number ;
        propertyChanged("number", this.number);
        return (Risk)this;
    }

    public final boolean isNumber(int number) {
        return equals(this.number, number);
    }

    private transient NumberModel numberModel;

    public NumberModel getNumberModel() {
        if (numberModel == null) numberModel = createNumberModel();
        return numberModel;
    }

    protected NumberModel createNumberModel() { return new NumberModel(); }

    protected class NumberModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "Risk_number";
        }

        @Override
        public java.lang.Integer getValue() {
            return getNumber();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setNumber(value);
        }

            @Override
            public void increment() {
                setNumber(getNumber() + 1);
            }

            @Override
            public void decrement() {
                setNumber(getNumber() - 1);
            }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- label ---

    private java.lang.String label ;

    public final java.lang.String getLabel() {
        return this.label ;
    }

    public final Risk setLabel(java.lang.String label) {
        if (isLabel(label)) return (Risk)this;
        if (ilarkesto.core.base.Str.isBlank(label)) throw new RuntimeException("Field is mandatory.");
        this.label = label ;
        propertyChanged("label", this.label);
        return (Risk)this;
    }

    public final boolean isLabel(java.lang.String label) {
        return equals(this.label, label);
    }

    private transient LabelModel labelModel;

    public LabelModel getLabelModel() {
        if (labelModel == null) labelModel = createLabelModel();
        return labelModel;
    }

    protected LabelModel createLabelModel() { return new LabelModel(); }

    protected class LabelModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Risk_label";
        }

        @Override
        public java.lang.String getValue() {
            return getLabel();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLabel(value);
        }

        @Override
        public boolean isMandatory() { return true; }
        @Override
        public String getTooltip() { return "The label should be short (as it appears where the Risk is referenced), yet give a hint strong enough to make the content of it come to mind."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- description ---

    private java.lang.String description ;

    public final java.lang.String getDescription() {
        return this.description ;
    }

    public final Risk setDescription(java.lang.String description) {
        if (isDescription(description)) return (Risk)this;
        this.description = description ;
        propertyChanged("description", this.description);
        return (Risk)this;
    }

    public final boolean isDescription(java.lang.String description) {
        return equals(this.description, description);
    }

    private transient DescriptionModel descriptionModel;

    public DescriptionModel getDescriptionModel() {
        if (descriptionModel == null) descriptionModel = createDescriptionModel();
        return descriptionModel;
    }

    protected DescriptionModel createDescriptionModel() { return new DescriptionModel(); }

    protected class DescriptionModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Risk_description";
        }

        @Override
        public java.lang.String getValue() {
            return getDescription();
        }

        @Override
        public void setValue(java.lang.String value) {
            setDescription(value);
        }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "The description of a Risk should give additional information like why or when it might occur, what would be affected and how; reasons, preconditions and damage that might result."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- probabilityMitigation ---

    private java.lang.String probabilityMitigation ;

    public final java.lang.String getProbabilityMitigation() {
        return this.probabilityMitigation ;
    }

    public final Risk setProbabilityMitigation(java.lang.String probabilityMitigation) {
        if (isProbabilityMitigation(probabilityMitigation)) return (Risk)this;
        this.probabilityMitigation = probabilityMitigation ;
        propertyChanged("probabilityMitigation", this.probabilityMitigation);
        return (Risk)this;
    }

    public final boolean isProbabilityMitigation(java.lang.String probabilityMitigation) {
        return equals(this.probabilityMitigation, probabilityMitigation);
    }

    private transient ProbabilityMitigationModel probabilityMitigationModel;

    public ProbabilityMitigationModel getProbabilityMitigationModel() {
        if (probabilityMitigationModel == null) probabilityMitigationModel = createProbabilityMitigationModel();
        return probabilityMitigationModel;
    }

    protected ProbabilityMitigationModel createProbabilityMitigationModel() { return new ProbabilityMitigationModel(); }

    protected class ProbabilityMitigationModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Risk_probabilityMitigation";
        }

        @Override
        public java.lang.String getValue() {
            return getProbabilityMitigation();
        }

        @Override
        public void setValue(java.lang.String value) {
            setProbabilityMitigation(value);
        }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "The mitigation plans that should be put into practice in order to mitigate the probability of a problem arising."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- impactMitigation ---

    private java.lang.String impactMitigation ;

    public final java.lang.String getImpactMitigation() {
        return this.impactMitigation ;
    }

    public final Risk setImpactMitigation(java.lang.String impactMitigation) {
        if (isImpactMitigation(impactMitigation)) return (Risk)this;
        this.impactMitigation = impactMitigation ;
        propertyChanged("impactMitigation", this.impactMitigation);
        return (Risk)this;
    }

    public final boolean isImpactMitigation(java.lang.String impactMitigation) {
        return equals(this.impactMitigation, impactMitigation);
    }

    private transient ImpactMitigationModel impactMitigationModel;

    public ImpactMitigationModel getImpactMitigationModel() {
        if (impactMitigationModel == null) impactMitigationModel = createImpactMitigationModel();
        return impactMitigationModel;
    }

    protected ImpactMitigationModel createImpactMitigationModel() { return new ImpactMitigationModel(); }

    protected class ImpactMitigationModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Risk_impactMitigation";
        }

        @Override
        public java.lang.String getValue() {
            return getImpactMitigation();
        }

        @Override
        public void setValue(java.lang.String value) {
            setImpactMitigation(value);
        }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "The mitigation plans that should be put into practice in order to mitigate the impact, should a problem arise."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- probability ---

    private int probability ;

    public final int getProbability() {
        return this.probability ;
    }

    public final Risk setProbability(int probability) {
        if (isProbability(probability)) return (Risk)this;
        this.probability = probability ;
        propertyChanged("probability", this.probability);
        return (Risk)this;
    }

    public abstract List<java.lang.Integer> getProbabilityOptions();

    public final boolean isProbability(int probability) {
        return equals(this.probability, probability);
    }

    private transient ProbabilityModel probabilityModel;

    public ProbabilityModel getProbabilityModel() {
        if (probabilityModel == null) probabilityModel = createProbabilityModel();
        return probabilityModel;
    }

    protected ProbabilityModel createProbabilityModel() { return new ProbabilityModel(); }

    protected class ProbabilityModel extends ilarkesto.gwt.client.editor.AOptionEditorModel<java.lang.Integer> {

        @Override
        public String getId() {
            return "Risk_probability";
        }

        @Override
        public java.lang.Integer getValue() {
            return getProbability();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setProbability(value);
        }

        @Override
        public List<java.lang.Integer> getOptions() {
            return getProbabilityOptions();
        }

        @Override
        public boolean isEditable() { return GRisk.this.isPriorityEditable(); }
        @Override
        public String getTooltip() { return "How probable is it (concidering the description and implemented minigation plans) that this Risk turns into a problem?"; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- impact ---

    private int impact ;

    public final int getImpact() {
        return this.impact ;
    }

    public final Risk setImpact(int impact) {
        if (isImpact(impact)) return (Risk)this;
        this.impact = impact ;
        propertyChanged("impact", this.impact);
        return (Risk)this;
    }

    public abstract List<java.lang.Integer> getImpactOptions();

    public final boolean isImpact(int impact) {
        return equals(this.impact, impact);
    }

    private transient ImpactModel impactModel;

    public ImpactModel getImpactModel() {
        if (impactModel == null) impactModel = createImpactModel();
        return impactModel;
    }

    protected ImpactModel createImpactModel() { return new ImpactModel(); }

    protected class ImpactModel extends ilarkesto.gwt.client.editor.AOptionEditorModel<java.lang.Integer> {

        @Override
        public String getId() {
            return "Risk_impact";
        }

        @Override
        public java.lang.Integer getValue() {
            return getImpact();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setImpact(value);
        }

        @Override
        public List<java.lang.Integer> getOptions() {
            return getImpactOptions();
        }

        @Override
        public boolean isEditable() { return GRisk.this.isPriorityEditable(); }
        @Override
        public String getTooltip() { return "How high is the impact (concidering the decription and the implemented mitigation plans), should a problem arise?"; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- update properties by map ---

    public void updateProperties(Map props) {
        projectId = (String) props.get("projectId");
        number  = (Integer) props.get("number");
        label  = (java.lang.String) props.get("label");
        description  = (java.lang.String) props.get("description");
        probabilityMitigation  = (java.lang.String) props.get("probabilityMitigation");
        impactMitigation  = (java.lang.String) props.get("impactMitigation");
        probability  = (Integer) props.get("probability");
        impact  = (Integer) props.get("impact");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("number", this.number);
        properties.put("label", this.label);
        properties.put("description", this.description);
        properties.put("probabilityMitigation", this.probabilityMitigation);
        properties.put("impactMitigation", this.impactMitigation);
        properties.put("probability", this.probability);
        properties.put("impact", this.impact);
    }

    @Override
    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getDescription(), key)) return true;
        if (matchesKey(getProbabilityMitigation(), key)) return true;
        if (matchesKey(getImpactMitigation(), key)) return true;
        return false;
    }

}