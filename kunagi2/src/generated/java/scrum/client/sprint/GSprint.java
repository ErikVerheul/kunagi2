// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GSprint
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public abstract boolean isEditable();

    public abstract boolean isPlanningEditable();

    public abstract boolean isReviewEditable();

    public abstract boolean isRetrospectiveEditable();

    public abstract boolean isDatesEditable();

    public GSprint() {
    }

    public GSprint(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "sprint";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- number ---

    private int number ;

    public final int getNumber() {
        return this.number ;
    }

    public final Sprint setNumber(int number) {
        if (isNumber(number)) return (Sprint)this;
        this.number = number ;
        propertyChanged("number", this.number);
        return (Sprint)this;
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
            return "Sprint_number";
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

    // --- project ---

    private String projectId;

    public final scrum.client.project.Project getProject() {
        if (projectId == null) return null;
        return getDao().getProject(this.projectId);
    }

    public final boolean isProjectSet() {
        return projectId != null;
    }

    public final Sprint setProject(scrum.client.project.Project project) {
        String id = project == null ? null : project.getId();
        if (equals(this.projectId, id)) return (Sprint) this;
        this.projectId = id;
        propertyChanged("projectId", this.projectId);
        return (Sprint)this;
    }

    public final boolean isProject(scrum.client.project.Project project) {
        return equals(this.projectId, project);
    }

    // --- label ---

    private java.lang.String label ;

    public final java.lang.String getLabel() {
        return this.label ;
    }

    public final Sprint setLabel(java.lang.String label) {
        if (isLabel(label)) return (Sprint)this;
        if (ilarkesto.core.base.Str.isBlank(label)) throw new RuntimeException("Field is mandatory.");
        this.label = label ;
        propertyChanged("label", this.label);
        return (Sprint)this;
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
            return "Sprint_label";
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
        public boolean isEditable() { return GSprint.this.isEditable(); }
        @Override
        public String getTooltip() { return "The label should be short (as it appears where the Sprint is referenced), yet give a hint strong enough to make the content of it come to mind."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- goal ---

    private java.lang.String goal ;

    public final java.lang.String getGoal() {
        return this.goal ;
    }

    public final Sprint setGoal(java.lang.String goal) {
        if (isGoal(goal)) return (Sprint)this;
        this.goal = goal ;
        propertyChanged("goal", this.goal);
        return (Sprint)this;
    }

    public abstract String getGoalTemplate();

    public final boolean isGoal(java.lang.String goal) {
        return equals(this.goal, goal);
    }

    private transient GoalModel goalModel;

    public GoalModel getGoalModel() {
        if (goalModel == null) goalModel = createGoalModel();
        return goalModel;
    }

    protected GoalModel createGoalModel() { return new GoalModel(); }

    protected class GoalModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Sprint_goal";
        }

        @Override
        public java.lang.String getValue() {
            return getGoal();
        }

        @Override
        public void setValue(java.lang.String value) {
            setGoal(value);
        }

        @Override
        public boolean isEditable() { return GSprint.this.isEditable(); }

        @Override
        public boolean isRichtext() { return true; }

        @Override
        public String getTemplate() { return getGoalTemplate(); }
        @Override
        public String getTooltip() { return "The goal is used to summarize the purpose of this Sprint. Naturally, it should be a description on why it is important that the Stories currently high in the Product Backlog should be realized next."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- begin ---

    private ilarkesto.core.time.Date begin ;

    public final ilarkesto.core.time.Date getBegin() {
        return this.begin ;
    }

    public final Sprint setBegin(ilarkesto.core.time.Date begin) {
        if (isBegin(begin)) return (Sprint)this;
        this.begin = begin ;
        propertyChanged("begin", this.begin);
        return (Sprint)this;
    }

    public final boolean isBegin(ilarkesto.core.time.Date begin) {
        return equals(this.begin, begin);
    }

    private transient BeginModel beginModel;

    public BeginModel getBeginModel() {
        if (beginModel == null) beginModel = createBeginModel();
        return beginModel;
    }

    protected BeginModel createBeginModel() { return new BeginModel(); }

    protected class BeginModel extends ilarkesto.gwt.client.editor.ADateEditorModel {

        @Override
        public String getId() {
            return "Sprint_begin";
        }

        @Override
        public ilarkesto.core.time.Date getValue() {
            return getBegin();
        }

        @Override
        public void setValue(ilarkesto.core.time.Date value) {
            setBegin(value);
        }

        @Override
        public boolean isEditable() { return GSprint.this.isDatesEditable(); }
        @Override
        public String getTooltip() { return "The date the Team starts working on the Sprint."; }

        @Override
        protected void onChangeValue(ilarkesto.core.time.Date oldValue, ilarkesto.core.time.Date newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- end ---

    private ilarkesto.core.time.Date end ;

    public final ilarkesto.core.time.Date getEnd() {
        return this.end ;
    }

    public final Sprint setEnd(ilarkesto.core.time.Date end) {
        if (isEnd(end)) return (Sprint)this;
        this.end = end ;
        propertyChanged("end", this.end);
        return (Sprint)this;
    }

    public final boolean isEnd(ilarkesto.core.time.Date end) {
        return equals(this.end, end);
    }

    private transient EndModel endModel;

    public EndModel getEndModel() {
        if (endModel == null) endModel = createEndModel();
        return endModel;
    }

    protected EndModel createEndModel() { return new EndModel(); }

    protected class EndModel extends ilarkesto.gwt.client.editor.ADateEditorModel {

        @Override
        public String getId() {
            return "Sprint_end";
        }

        @Override
        public ilarkesto.core.time.Date getValue() {
            return getEnd();
        }

        @Override
        public void setValue(ilarkesto.core.time.Date value) {
            setEnd(value);
        }

        @Override
        public boolean isEditable() { return GSprint.this.isDatesEditable(); }
        @Override
        public String getTooltip() { return "The date by which the Team will finish working on this Sprint. A Sprint Review meeting should be scheduled to present results."; }

        @Override
        protected void onChangeValue(ilarkesto.core.time.Date oldValue, ilarkesto.core.time.Date newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- velocity ---

    private java.lang.Float velocity ;

    public final java.lang.Float getVelocity() {
        return this.velocity ;
    }

    public final Sprint setVelocity(java.lang.Float velocity) {
        if (isVelocity(velocity)) return (Sprint)this;
        this.velocity = velocity ;
        propertyChanged("velocity", this.velocity);
        return (Sprint)this;
    }

    public final boolean isVelocity(java.lang.Float velocity) {
        return equals(this.velocity, velocity);
    }

    private transient VelocityModel velocityModel;

    public VelocityModel getVelocityModel() {
        if (velocityModel == null) velocityModel = createVelocityModel();
        return velocityModel;
    }

    protected VelocityModel createVelocityModel() { return new VelocityModel(); }

    protected class VelocityModel extends ilarkesto.gwt.client.editor.AFloatEditorModel {

        @Override
        public String getId() {
            return "Sprint_velocity";
        }

        @Override
        public java.lang.Float getValue() {
            return getVelocity();
        }

        @Override
        public void setValue(java.lang.Float value) {
            setVelocity(value);
        }

        @Override
        protected void onChangeValue(java.lang.Float oldValue, java.lang.Float newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- completedRequirementsData ---

    private java.lang.String completedRequirementsData ;

    public final java.lang.String getCompletedRequirementsData() {
        return this.completedRequirementsData ;
    }

    public final Sprint setCompletedRequirementsData(java.lang.String completedRequirementsData) {
        if (isCompletedRequirementsData(completedRequirementsData)) return (Sprint)this;
        this.completedRequirementsData = completedRequirementsData ;
        propertyChanged("completedRequirementsData", this.completedRequirementsData);
        return (Sprint)this;
    }

    public final boolean isCompletedRequirementsData(java.lang.String completedRequirementsData) {
        return equals(this.completedRequirementsData, completedRequirementsData);
    }

    private transient CompletedRequirementsDataModel completedRequirementsDataModel;

    public CompletedRequirementsDataModel getCompletedRequirementsDataModel() {
        if (completedRequirementsDataModel == null) completedRequirementsDataModel = createCompletedRequirementsDataModel();
        return completedRequirementsDataModel;
    }

    protected CompletedRequirementsDataModel createCompletedRequirementsDataModel() { return new CompletedRequirementsDataModel(); }

    protected class CompletedRequirementsDataModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Sprint_completedRequirementsData";
        }

        @Override
        public java.lang.String getValue() {
            return getCompletedRequirementsData();
        }

        @Override
        public void setValue(java.lang.String value) {
            setCompletedRequirementsData(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- incompletedRequirementsData ---

    private java.lang.String incompletedRequirementsData ;

    public final java.lang.String getIncompletedRequirementsData() {
        return this.incompletedRequirementsData ;
    }

    public final Sprint setIncompletedRequirementsData(java.lang.String incompletedRequirementsData) {
        if (isIncompletedRequirementsData(incompletedRequirementsData)) return (Sprint)this;
        this.incompletedRequirementsData = incompletedRequirementsData ;
        propertyChanged("incompletedRequirementsData", this.incompletedRequirementsData);
        return (Sprint)this;
    }

    public final boolean isIncompletedRequirementsData(java.lang.String incompletedRequirementsData) {
        return equals(this.incompletedRequirementsData, incompletedRequirementsData);
    }

    private transient IncompletedRequirementsDataModel incompletedRequirementsDataModel;

    public IncompletedRequirementsDataModel getIncompletedRequirementsDataModel() {
        if (incompletedRequirementsDataModel == null) incompletedRequirementsDataModel = createIncompletedRequirementsDataModel();
        return incompletedRequirementsDataModel;
    }

    protected IncompletedRequirementsDataModel createIncompletedRequirementsDataModel() { return new IncompletedRequirementsDataModel(); }

    protected class IncompletedRequirementsDataModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Sprint_incompletedRequirementsData";
        }

        @Override
        public java.lang.String getValue() {
            return getIncompletedRequirementsData();
        }

        @Override
        public void setValue(java.lang.String value) {
            setIncompletedRequirementsData(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- planningNote ---

    private java.lang.String planningNote ;

    public final java.lang.String getPlanningNote() {
        return this.planningNote ;
    }

    public final Sprint setPlanningNote(java.lang.String planningNote) {
        if (isPlanningNote(planningNote)) return (Sprint)this;
        this.planningNote = planningNote ;
        propertyChanged("planningNote", this.planningNote);
        return (Sprint)this;
    }

    public abstract String getPlanningNoteTemplate();

    public final boolean isPlanningNote(java.lang.String planningNote) {
        return equals(this.planningNote, planningNote);
    }

    private transient PlanningNoteModel planningNoteModel;

    public PlanningNoteModel getPlanningNoteModel() {
        if (planningNoteModel == null) planningNoteModel = createPlanningNoteModel();
        return planningNoteModel;
    }

    protected PlanningNoteModel createPlanningNoteModel() { return new PlanningNoteModel(); }

    protected class PlanningNoteModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Sprint_planningNote";
        }

        @Override
        public java.lang.String getValue() {
            return getPlanningNote();
        }

        @Override
        public void setValue(java.lang.String value) {
            setPlanningNote(value);
        }

        @Override
        public boolean isEditable() { return GSprint.this.isPlanningEditable(); }

        @Override
        public boolean isRichtext() { return true; }

        @Override
        public String getTemplate() { return getPlanningNoteTemplate(); }
        @Override
        public String getTooltip() { return "Things that come up during Sprint Planning that might affect the Sprint or be of interest for stakeholders (illness, vacation and other influences; discussion results, agreements, etc."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- reviewNote ---

    private java.lang.String reviewNote ;

    public final java.lang.String getReviewNote() {
        return this.reviewNote ;
    }

    public final Sprint setReviewNote(java.lang.String reviewNote) {
        if (isReviewNote(reviewNote)) return (Sprint)this;
        this.reviewNote = reviewNote ;
        propertyChanged("reviewNote", this.reviewNote);
        return (Sprint)this;
    }

    public abstract String getReviewNoteTemplate();

    public final boolean isReviewNote(java.lang.String reviewNote) {
        return equals(this.reviewNote, reviewNote);
    }

    private transient ReviewNoteModel reviewNoteModel;

    public ReviewNoteModel getReviewNoteModel() {
        if (reviewNoteModel == null) reviewNoteModel = createReviewNoteModel();
        return reviewNoteModel;
    }

    protected ReviewNoteModel createReviewNoteModel() { return new ReviewNoteModel(); }

    protected class ReviewNoteModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Sprint_reviewNote";
        }

        @Override
        public java.lang.String getValue() {
            return getReviewNote();
        }

        @Override
        public void setValue(java.lang.String value) {
            setReviewNote(value);
        }

        @Override
        public boolean isEditable() { return GSprint.this.isReviewEditable(); }

        @Override
        public boolean isRichtext() { return true; }

        @Override
        public String getTemplate() { return getReviewNoteTemplate(); }
        @Override
        public String getTooltip() { return "Things that come up during the Sprint Review that might be of interest for stakeholders."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- retrospectiveNote ---

    private java.lang.String retrospectiveNote ;

    public final java.lang.String getRetrospectiveNote() {
        return this.retrospectiveNote ;
    }

    public final Sprint setRetrospectiveNote(java.lang.String retrospectiveNote) {
        if (isRetrospectiveNote(retrospectiveNote)) return (Sprint)this;
        this.retrospectiveNote = retrospectiveNote ;
        propertyChanged("retrospectiveNote", this.retrospectiveNote);
        return (Sprint)this;
    }

    public abstract String getRetrospectiveNoteTemplate();

    public final boolean isRetrospectiveNote(java.lang.String retrospectiveNote) {
        return equals(this.retrospectiveNote, retrospectiveNote);
    }

    private transient RetrospectiveNoteModel retrospectiveNoteModel;

    public RetrospectiveNoteModel getRetrospectiveNoteModel() {
        if (retrospectiveNoteModel == null) retrospectiveNoteModel = createRetrospectiveNoteModel();
        return retrospectiveNoteModel;
    }

    protected RetrospectiveNoteModel createRetrospectiveNoteModel() { return new RetrospectiveNoteModel(); }

    protected class RetrospectiveNoteModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Sprint_retrospectiveNote";
        }

        @Override
        public java.lang.String getValue() {
            return getRetrospectiveNote();
        }

        @Override
        public void setValue(java.lang.String value) {
            setRetrospectiveNote(value);
        }

        @Override
        public boolean isEditable() { return GSprint.this.isRetrospectiveEditable(); }

        @Override
        public boolean isRichtext() { return true; }

        @Override
        public String getTemplate() { return getRetrospectiveNoteTemplate(); }
        @Override
        public String getTooltip() { return "Things that come up during Sprint Retrospectives that are important in the future or might be of interest for stakeholders."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- requirementsOrderIds ---

    private java.util.List<java.lang.String> requirementsOrderIds = new java.util.ArrayList<java.lang.String>();

    public final java.util.List<java.lang.String> getRequirementsOrderIds() {
        return new java.util.ArrayList<java.lang.String>(requirementsOrderIds);
    }

    public final void setRequirementsOrderIds(java.util.List<java.lang.String> requirementsOrderIds) {
        if (requirementsOrderIds == null) throw new IllegalArgumentException("null is not allowed");
        if (this.requirementsOrderIds.equals(requirementsOrderIds)) return;
        this.requirementsOrderIds = new java.util.ArrayList<java.lang.String>(requirementsOrderIds);
        propertyChanged("requirementsOrderIds", this.requirementsOrderIds);
    }

    public final boolean containsRequirementsOrderId(java.lang.String requirementsOrderId) {
        return requirementsOrderIds.contains(requirementsOrderId);
    }


    // --- productOwners ---

    private Set<String> productOwnersIds = new HashSet<String>();

    public final java.util.Set<scrum.client.admin.User> getProductOwners() {
        if ( productOwnersIds.isEmpty()) return Collections.emptySet();
        return getDao().getUsers(this.productOwnersIds);
    }

    public final void setProductOwners(Collection<scrum.client.admin.User> values) {
        productOwnersIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("productOwnersIds", this.productOwnersIds);
    }

    public final void addProductOwner(scrum.client.admin.User productOwner) {
        String id = productOwner.getId();
        if (productOwnersIds.contains(id)) return;
        productOwnersIds.add(id);
        propertyChanged("productOwnersIds", this.productOwnersIds);
    }

    public final void removeProductOwner(scrum.client.admin.User productOwner) {
        String id = productOwner.getId();
        if (!productOwnersIds.contains(id)) return;
        productOwnersIds.remove(id);
        propertyChanged("productOwnersIds", this.productOwnersIds);
    }

    public final boolean containsProductOwner(scrum.client.admin.User productOwner) {
        return productOwnersIds.contains(productOwner.getId());
    }


    // --- scrumMasters ---

    private Set<String> scrumMastersIds = new HashSet<String>();

    public final java.util.Set<scrum.client.admin.User> getScrumMasters() {
        if ( scrumMastersIds.isEmpty()) return Collections.emptySet();
        return getDao().getUsers(this.scrumMastersIds);
    }

    public final void setScrumMasters(Collection<scrum.client.admin.User> values) {
        scrumMastersIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("scrumMastersIds", this.scrumMastersIds);
    }

    public final void addScrumMaster(scrum.client.admin.User scrumMaster) {
        String id = scrumMaster.getId();
        if (scrumMastersIds.contains(id)) return;
        scrumMastersIds.add(id);
        propertyChanged("scrumMastersIds", this.scrumMastersIds);
    }

    public final void removeScrumMaster(scrum.client.admin.User scrumMaster) {
        String id = scrumMaster.getId();
        if (!scrumMastersIds.contains(id)) return;
        scrumMastersIds.remove(id);
        propertyChanged("scrumMastersIds", this.scrumMastersIds);
    }

    public final boolean containsScrumMaster(scrum.client.admin.User scrumMaster) {
        return scrumMastersIds.contains(scrumMaster.getId());
    }


    // --- teamMembers ---

    private Set<String> teamMembersIds = new HashSet<String>();

    public final java.util.Set<scrum.client.admin.User> getTeamMembers() {
        if ( teamMembersIds.isEmpty()) return Collections.emptySet();
        return getDao().getUsers(this.teamMembersIds);
    }

    public final void setTeamMembers(Collection<scrum.client.admin.User> values) {
        teamMembersIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("teamMembersIds", this.teamMembersIds);
    }

    public final void addTeamMember(scrum.client.admin.User teamMember) {
        String id = teamMember.getId();
        if (teamMembersIds.contains(id)) return;
        teamMembersIds.add(id);
        propertyChanged("teamMembersIds", this.teamMembersIds);
    }

    public final void removeTeamMember(scrum.client.admin.User teamMember) {
        String id = teamMember.getId();
        if (!teamMembersIds.contains(id)) return;
        teamMembersIds.remove(id);
        propertyChanged("teamMembersIds", this.teamMembersIds);
    }

    public final boolean containsTeamMember(scrum.client.admin.User teamMember) {
        return teamMembersIds.contains(teamMember.getId());
    }


    // --- update properties by map ---

    public void updateProperties(Map props) {
        number  = (Integer) props.get("number");
        projectId = (String) props.get("projectId");
        label  = (java.lang.String) props.get("label");
        goal  = (java.lang.String) props.get("goal");
        String beginAsString = (String) props.get("begin");
        begin  =  beginAsString == null ? null : new ilarkesto.core.time.Date(beginAsString);
        String endAsString = (String) props.get("end");
        end  =  endAsString == null ? null : new ilarkesto.core.time.Date(endAsString);
        velocity  = (java.lang.Float) props.get("velocity");
        completedRequirementsData  = (java.lang.String) props.get("completedRequirementsData");
        incompletedRequirementsData  = (java.lang.String) props.get("incompletedRequirementsData");
        planningNote  = (java.lang.String) props.get("planningNote");
        reviewNote  = (java.lang.String) props.get("reviewNote");
        retrospectiveNote  = (java.lang.String) props.get("retrospectiveNote");
        requirementsOrderIds  = (java.util.List<java.lang.String>) props.get("requirementsOrderIds");
        productOwnersIds = (Set<String>) props.get("productOwnersIds");
        scrumMastersIds = (Set<String>) props.get("scrumMastersIds");
        teamMembersIds = (Set<String>) props.get("teamMembersIds");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("number", this.number);
        properties.put("projectId", this.projectId);
        properties.put("label", this.label);
        properties.put("goal", this.goal);
        properties.put("begin", this.begin == null ? null : this.begin.toString());
        properties.put("end", this.end == null ? null : this.end.toString());
        properties.put("velocity", this.velocity);
        properties.put("completedRequirementsData", this.completedRequirementsData);
        properties.put("incompletedRequirementsData", this.incompletedRequirementsData);
        properties.put("planningNote", this.planningNote);
        properties.put("reviewNote", this.reviewNote);
        properties.put("retrospectiveNote", this.retrospectiveNote);
        properties.put("requirementsOrderIds", this.requirementsOrderIds);
        properties.put("productOwnersIds", this.productOwnersIds);
        properties.put("scrumMastersIds", this.scrumMastersIds);
        properties.put("teamMembersIds", this.teamMembersIds);
    }

    public final java.util.List<scrum.client.project.Project> getCurrentSprintProjects() {
        return getDao().getProjectsByCurrentSprint((Sprint)this);
    }

    public final java.util.List<scrum.client.project.Project> getNextSprintProjects() {
        return getDao().getProjectsByNextSprint((Sprint)this);
    }


    public final scrum.client.sprint.SprintReport getSprintReport() {
        return getDao().getSprintReportBySprint((Sprint)this);
    }

    public final java.util.List<scrum.client.project.Requirement> getRequirements() {
        return getDao().getRequirementsBySprint((Sprint)this);
    }

    public final java.util.List<scrum.client.release.Release> getReleases() {
        return getDao().getReleasesBySprint((Sprint)this);
    }

    public final java.util.List<scrum.client.sprint.Task> getClosedTasksInPasts() {
        return getDao().getTasksByClosedInPastSprint((Sprint)this);
    }


    @Override
    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getGoal(), key)) return true;
        if (matchesKey(getPlanningNote(), key)) return true;
        if (matchesKey(getReviewNote(), key)) return true;
        if (matchesKey(getRetrospectiveNote(), key)) return true;
        return false;
    }

}