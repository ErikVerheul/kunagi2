// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.issues;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GIssue
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GIssue() {
    }

    public GIssue(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "issue";

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

    public final Issue setProject(scrum.client.project.Project project) {
        String id = project == null ? null : project.getId();
        if (equals(this.projectId, id)) return (Issue) this;
        this.projectId = id;
        propertyChanged("projectId", this.projectId);
        return (Issue)this;
    }

    public final boolean isProject(scrum.client.project.Project project) {
        return equals(this.projectId, project);
    }

    // --- story ---

    private String storyId;

    public final scrum.client.project.Requirement getStory() {
        if (storyId == null) return null;
        return getDao().getRequirement(this.storyId);
    }

    public final boolean isStorySet() {
        return storyId != null;
    }

    public final Issue setStory(scrum.client.project.Requirement story) {
        String id = story == null ? null : story.getId();
        if (equals(this.storyId, id)) return (Issue) this;
        this.storyId = id;
        propertyChanged("storyId", this.storyId);
        return (Issue)this;
    }

    public final boolean isStory(scrum.client.project.Requirement story) {
        return equals(this.storyId, story);
    }

    // --- number ---

    private int number ;

    public final int getNumber() {
        return this.number ;
    }

    public final Issue setNumber(int number) {
        if (isNumber(number)) return (Issue)this;
        this.number = number ;
        propertyChanged("number", this.number);
        return (Issue)this;
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
            return "Issue_number";
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

    // --- type ---

    private java.lang.String type ;

    public final java.lang.String getType() {
        return this.type ;
    }

    public final Issue setType(java.lang.String type) {
        if (isType(type)) return (Issue)this;
        this.type = type ;
        propertyChanged("type", this.type);
        return (Issue)this;
    }

    public abstract List<java.lang.String> getTypeOptions();

    public final boolean isType(java.lang.String type) {
        return equals(this.type, type);
    }

    private transient TypeModel typeModel;

    public TypeModel getTypeModel() {
        if (typeModel == null) typeModel = createTypeModel();
        return typeModel;
    }

    protected TypeModel createTypeModel() { return new TypeModel(); }

    protected class TypeModel extends ilarkesto.gwt.client.editor.AOptionEditorModel<java.lang.String> {

        @Override
        public String getId() {
            return "Issue_type";
        }

        @Override
        public java.lang.String getValue() {
            return getType();
        }

        @Override
        public void setValue(java.lang.String value) {
            setType(value);
        }

        @Override
        public List<java.lang.String> getOptions() {
            return getTypeOptions();
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- date ---

    private ilarkesto.core.time.DateAndTime date ;

    public final ilarkesto.core.time.DateAndTime getDate() {
        return this.date ;
    }

    public final Issue setDate(ilarkesto.core.time.DateAndTime date) {
        if (isDate(date)) return (Issue)this;
        if (date == null) throw new RuntimeException("Field is mandatory.");
        this.date = date ;
        propertyChanged("date", this.date);
        return (Issue)this;
    }

    public final boolean isDate(ilarkesto.core.time.DateAndTime date) {
        return equals(this.date, date);
    }

    private transient DateModel dateModel;

    public DateModel getDateModel() {
        if (dateModel == null) dateModel = createDateModel();
        return dateModel;
    }

    protected DateModel createDateModel() { return new DateModel(); }

    protected class DateModel extends ilarkesto.gwt.client.editor.ADateAndTimeEditorModel {

        @Override
        public String getId() {
            return "Issue_date";
        }

        @Override
        public ilarkesto.core.time.DateAndTime getValue() {
            return getDate();
        }

        @Override
        public void setValue(ilarkesto.core.time.DateAndTime value) {
            setDate(value);
        }

        @Override
        public boolean isMandatory() { return true; }

        @Override
        protected void onChangeValue(ilarkesto.core.time.DateAndTime oldValue, ilarkesto.core.time.DateAndTime newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- creator ---

    private String creatorId;

    public final scrum.client.admin.User getCreator() {
        if (creatorId == null) return null;
        return getDao().getUser(this.creatorId);
    }

    public final boolean isCreatorSet() {
        return creatorId != null;
    }

    public final Issue setCreator(scrum.client.admin.User creator) {
        String id = creator == null ? null : creator.getId();
        if (equals(this.creatorId, id)) return (Issue) this;
        this.creatorId = id;
        propertyChanged("creatorId", this.creatorId);
        return (Issue)this;
    }

    public final boolean isCreator(scrum.client.admin.User creator) {
        return equals(this.creatorId, creator);
    }

    // --- label ---

    private java.lang.String label ;

    public final java.lang.String getLabel() {
        return this.label ;
    }

    public final Issue setLabel(java.lang.String label) {
        if (isLabel(label)) return (Issue)this;
        if (ilarkesto.core.base.Str.isBlank(label)) throw new RuntimeException("Field is mandatory.");
        this.label = label ;
        propertyChanged("label", this.label);
        return (Issue)this;
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
            return "Issue_label";
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
        public String getTooltip() { return "The label should be short (as it appears where the Issue is referenced), yet give a hint strong enough to make the content of it come to mind."; }

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

    public final Issue setDescription(java.lang.String description) {
        if (isDescription(description)) return (Issue)this;
        this.description = description ;
        propertyChanged("description", this.description);
        return (Issue)this;
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
            return "Issue_description";
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
        public String getTooltip() { return "The description of an issue should give enough information for other people to understand what the issue is about. That contains information on how to reproduce an issue and what symptoms are, as well as suggestions on how to fix it."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- statement ---

    private java.lang.String statement ;

    public final java.lang.String getStatement() {
        return this.statement ;
    }

    public final Issue setStatement(java.lang.String statement) {
        if (isStatement(statement)) return (Issue)this;
        this.statement = statement ;
        propertyChanged("statement", this.statement);
        return (Issue)this;
    }

    public final boolean isStatement(java.lang.String statement) {
        return equals(this.statement, statement);
    }

    private transient StatementModel statementModel;

    public StatementModel getStatementModel() {
        if (statementModel == null) statementModel = createStatementModel();
        return statementModel;
    }

    protected StatementModel createStatementModel() { return new StatementModel(); }

    protected class StatementModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Issue_statement";
        }

        @Override
        public java.lang.String getValue() {
            return getStatement();
        }

        @Override
        public void setValue(java.lang.String value) {
            setStatement(value);
        }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "Official statement from the Scrum Team to the public about this issue. This could be a  workaround description, the reason or some other information about status of the  issue."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- issuerName ---

    private java.lang.String issuerName ;

    public final java.lang.String getIssuerName() {
        return this.issuerName ;
    }

    public final Issue setIssuerName(java.lang.String issuerName) {
        if (isIssuerName(issuerName)) return (Issue)this;
        this.issuerName = issuerName ;
        propertyChanged("issuerName", this.issuerName);
        return (Issue)this;
    }

    public final boolean isIssuerName(java.lang.String issuerName) {
        return equals(this.issuerName, issuerName);
    }

    private transient IssuerNameModel issuerNameModel;

    public IssuerNameModel getIssuerNameModel() {
        if (issuerNameModel == null) issuerNameModel = createIssuerNameModel();
        return issuerNameModel;
    }

    protected IssuerNameModel createIssuerNameModel() { return new IssuerNameModel(); }

    protected class IssuerNameModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Issue_issuerName";
        }

        @Override
        public java.lang.String getValue() {
            return getIssuerName();
        }

        @Override
        public void setValue(java.lang.String value) {
            setIssuerName(value);
        }
        @Override
        public String getTooltip() { return "The person who filed this issue."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- issuerEmail ---

    private java.lang.String issuerEmail ;

    public final java.lang.String getIssuerEmail() {
        return this.issuerEmail ;
    }

    public final Issue setIssuerEmail(java.lang.String issuerEmail) {
        if (isIssuerEmail(issuerEmail)) return (Issue)this;
        this.issuerEmail = issuerEmail ;
        propertyChanged("issuerEmail", this.issuerEmail);
        return (Issue)this;
    }

    public final boolean isIssuerEmail(java.lang.String issuerEmail) {
        return equals(this.issuerEmail, issuerEmail);
    }

    private transient IssuerEmailModel issuerEmailModel;

    public IssuerEmailModel getIssuerEmailModel() {
        if (issuerEmailModel == null) issuerEmailModel = createIssuerEmailModel();
        return issuerEmailModel;
    }

    protected IssuerEmailModel createIssuerEmailModel() { return new IssuerEmailModel(); }

    protected class IssuerEmailModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Issue_issuerEmail";
        }

        @Override
        public java.lang.String getValue() {
            return getIssuerEmail();
        }

        @Override
        public void setValue(java.lang.String value) {
            setIssuerEmail(value);
        }
        @Override
        public String getTooltip() { return "E-Mail address of the person, who filed this issue."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- acceptDate ---

    private ilarkesto.core.time.Date acceptDate ;

    public final ilarkesto.core.time.Date getAcceptDate() {
        return this.acceptDate ;
    }

    public final Issue setAcceptDate(ilarkesto.core.time.Date acceptDate) {
        if (isAcceptDate(acceptDate)) return (Issue)this;
        this.acceptDate = acceptDate ;
        propertyChanged("acceptDate", this.acceptDate);
        return (Issue)this;
    }

    public final boolean isAcceptDate(ilarkesto.core.time.Date acceptDate) {
        return equals(this.acceptDate, acceptDate);
    }

    private transient AcceptDateModel acceptDateModel;

    public AcceptDateModel getAcceptDateModel() {
        if (acceptDateModel == null) acceptDateModel = createAcceptDateModel();
        return acceptDateModel;
    }

    protected AcceptDateModel createAcceptDateModel() { return new AcceptDateModel(); }

    protected class AcceptDateModel extends ilarkesto.gwt.client.editor.ADateEditorModel {

        @Override
        public String getId() {
            return "Issue_acceptDate";
        }

        @Override
        public ilarkesto.core.time.Date getValue() {
            return getAcceptDate();
        }

        @Override
        public void setValue(ilarkesto.core.time.Date value) {
            setAcceptDate(value);
        }

        @Override
        protected void onChangeValue(ilarkesto.core.time.Date oldValue, ilarkesto.core.time.Date newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- urgent ---

    private boolean urgent ;

    public final boolean isUrgent() {
        return this.urgent ;
    }

    public final Issue setUrgent(boolean urgent) {
        if (isUrgent(urgent)) return (Issue)this;
        this.urgent = urgent ;
        propertyChanged("urgent", this.urgent);
        return (Issue)this;
    }

    public final boolean isUrgent(boolean urgent) {
        return equals(this.urgent, urgent);
    }

    private transient UrgentModel urgentModel;

    public UrgentModel getUrgentModel() {
        if (urgentModel == null) urgentModel = createUrgentModel();
        return urgentModel;
    }

    protected UrgentModel createUrgentModel() { return new UrgentModel(); }

    protected class UrgentModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "Issue_urgent";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isUrgent();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setUrgent(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- severity ---

    private int severity ;

    public final int getSeverity() {
        return this.severity ;
    }

    public final Issue setSeverity(int severity) {
        if (isSeverity(severity)) return (Issue)this;
        this.severity = severity ;
        propertyChanged("severity", this.severity);
        return (Issue)this;
    }

    public abstract List<java.lang.Integer> getSeverityOptions();

    public final boolean isSeverity(int severity) {
        return equals(this.severity, severity);
    }

    private transient SeverityModel severityModel;

    public SeverityModel getSeverityModel() {
        if (severityModel == null) severityModel = createSeverityModel();
        return severityModel;
    }

    protected SeverityModel createSeverityModel() { return new SeverityModel(); }

    protected class SeverityModel extends ilarkesto.gwt.client.editor.AOptionEditorModel<java.lang.Integer> {

        @Override
        public String getId() {
            return "Issue_severity";
        }

        @Override
        public java.lang.Integer getValue() {
            return getSeverity();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setSeverity(value);
        }

        @Override
        public List<java.lang.Integer> getOptions() {
            return getSeverityOptions();
        }
        @Override
        public String getTooltip() { return "The level of this bug's impact. A minor bug might be a cosmetic failure, a normal bug encumbers the user's work, a severe bug might lead to loss of data or property, a critical bug makes product usage impossible."; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- owner ---

    private String ownerId;

    public final scrum.client.admin.User getOwner() {
        if (ownerId == null) return null;
        return getDao().getUser(this.ownerId);
    }

    public final boolean isOwnerSet() {
        return ownerId != null;
    }

    public final Issue setOwner(scrum.client.admin.User owner) {
        String id = owner == null ? null : owner.getId();
        if (equals(this.ownerId, id)) return (Issue) this;
        this.ownerId = id;
        propertyChanged("ownerId", this.ownerId);
        return (Issue)this;
    }

    public final boolean isOwner(scrum.client.admin.User owner) {
        return equals(this.ownerId, owner);
    }

    // --- fixDate ---

    private ilarkesto.core.time.Date fixDate ;

    public final ilarkesto.core.time.Date getFixDate() {
        return this.fixDate ;
    }

    public final Issue setFixDate(ilarkesto.core.time.Date fixDate) {
        if (isFixDate(fixDate)) return (Issue)this;
        this.fixDate = fixDate ;
        propertyChanged("fixDate", this.fixDate);
        return (Issue)this;
    }

    public final boolean isFixDate(ilarkesto.core.time.Date fixDate) {
        return equals(this.fixDate, fixDate);
    }

    private transient FixDateModel fixDateModel;

    public FixDateModel getFixDateModel() {
        if (fixDateModel == null) fixDateModel = createFixDateModel();
        return fixDateModel;
    }

    protected FixDateModel createFixDateModel() { return new FixDateModel(); }

    protected class FixDateModel extends ilarkesto.gwt.client.editor.ADateEditorModel {

        @Override
        public String getId() {
            return "Issue_fixDate";
        }

        @Override
        public ilarkesto.core.time.Date getValue() {
            return getFixDate();
        }

        @Override
        public void setValue(ilarkesto.core.time.Date value) {
            setFixDate(value);
        }

        @Override
        protected void onChangeValue(ilarkesto.core.time.Date oldValue, ilarkesto.core.time.Date newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- closeDate ---

    private ilarkesto.core.time.Date closeDate ;

    public final ilarkesto.core.time.Date getCloseDate() {
        return this.closeDate ;
    }

    public final Issue setCloseDate(ilarkesto.core.time.Date closeDate) {
        if (isCloseDate(closeDate)) return (Issue)this;
        this.closeDate = closeDate ;
        propertyChanged("closeDate", this.closeDate);
        return (Issue)this;
    }

    public final boolean isCloseDate(ilarkesto.core.time.Date closeDate) {
        return equals(this.closeDate, closeDate);
    }

    private transient CloseDateModel closeDateModel;

    public CloseDateModel getCloseDateModel() {
        if (closeDateModel == null) closeDateModel = createCloseDateModel();
        return closeDateModel;
    }

    protected CloseDateModel createCloseDateModel() { return new CloseDateModel(); }

    protected class CloseDateModel extends ilarkesto.gwt.client.editor.ADateEditorModel {

        @Override
        public String getId() {
            return "Issue_closeDate";
        }

        @Override
        public ilarkesto.core.time.Date getValue() {
            return getCloseDate();
        }

        @Override
        public void setValue(ilarkesto.core.time.Date value) {
            setCloseDate(value);
        }

        @Override
        protected void onChangeValue(ilarkesto.core.time.Date oldValue, ilarkesto.core.time.Date newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- suspendedUntilDate ---

    private ilarkesto.core.time.Date suspendedUntilDate ;

    public final ilarkesto.core.time.Date getSuspendedUntilDate() {
        return this.suspendedUntilDate ;
    }

    public final Issue setSuspendedUntilDate(ilarkesto.core.time.Date suspendedUntilDate) {
        if (isSuspendedUntilDate(suspendedUntilDate)) return (Issue)this;
        this.suspendedUntilDate = suspendedUntilDate ;
        propertyChanged("suspendedUntilDate", this.suspendedUntilDate);
        return (Issue)this;
    }

    public final boolean isSuspendedUntilDate(ilarkesto.core.time.Date suspendedUntilDate) {
        return equals(this.suspendedUntilDate, suspendedUntilDate);
    }

    private transient SuspendedUntilDateModel suspendedUntilDateModel;

    public SuspendedUntilDateModel getSuspendedUntilDateModel() {
        if (suspendedUntilDateModel == null) suspendedUntilDateModel = createSuspendedUntilDateModel();
        return suspendedUntilDateModel;
    }

    protected SuspendedUntilDateModel createSuspendedUntilDateModel() { return new SuspendedUntilDateModel(); }

    protected class SuspendedUntilDateModel extends ilarkesto.gwt.client.editor.ADateEditorModel {

        @Override
        public String getId() {
            return "Issue_suspendedUntilDate";
        }

        @Override
        public ilarkesto.core.time.Date getValue() {
            return getSuspendedUntilDate();
        }

        @Override
        public void setValue(ilarkesto.core.time.Date value) {
            setSuspendedUntilDate(value);
        }

        @Override
        protected void onChangeValue(ilarkesto.core.time.Date oldValue, ilarkesto.core.time.Date newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- affectedReleases ---

    private Set<String> affectedReleasesIds = new HashSet<String>();

    public final java.util.Set<scrum.client.release.Release> getAffectedReleases() {
        if ( affectedReleasesIds.isEmpty()) return Collections.emptySet();
        return getDao().getReleases(this.affectedReleasesIds);
    }

    public final void setAffectedReleases(Collection<scrum.client.release.Release> values) {
        affectedReleasesIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("affectedReleasesIds", this.affectedReleasesIds);
    }

    public final void addAffectedRelease(scrum.client.release.Release affectedRelease) {
        String id = affectedRelease.getId();
        if (affectedReleasesIds.contains(id)) return;
        affectedReleasesIds.add(id);
        propertyChanged("affectedReleasesIds", this.affectedReleasesIds);
    }

    public final void removeAffectedRelease(scrum.client.release.Release affectedRelease) {
        String id = affectedRelease.getId();
        if (!affectedReleasesIds.contains(id)) return;
        affectedReleasesIds.remove(id);
        propertyChanged("affectedReleasesIds", this.affectedReleasesIds);
    }

    public final boolean containsAffectedRelease(scrum.client.release.Release affectedRelease) {
        return affectedReleasesIds.contains(affectedRelease.getId());
    }


    // --- fixReleases ---

    private Set<String> fixReleasesIds = new HashSet<String>();

    public final java.util.Set<scrum.client.release.Release> getFixReleases() {
        if ( fixReleasesIds.isEmpty()) return Collections.emptySet();
        return getDao().getReleases(this.fixReleasesIds);
    }

    public final void setFixReleases(Collection<scrum.client.release.Release> values) {
        fixReleasesIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("fixReleasesIds", this.fixReleasesIds);
    }

    public final void addFixRelease(scrum.client.release.Release fixRelease) {
        String id = fixRelease.getId();
        if (fixReleasesIds.contains(id)) return;
        fixReleasesIds.add(id);
        propertyChanged("fixReleasesIds", this.fixReleasesIds);
    }

    public final void removeFixRelease(scrum.client.release.Release fixRelease) {
        String id = fixRelease.getId();
        if (!fixReleasesIds.contains(id)) return;
        fixReleasesIds.remove(id);
        propertyChanged("fixReleasesIds", this.fixReleasesIds);
    }

    public final boolean containsFixRelease(scrum.client.release.Release fixRelease) {
        return fixReleasesIds.contains(fixRelease.getId());
    }


    // --- published ---

    private boolean published ;

    public final boolean isPublished() {
        return this.published ;
    }

    public final Issue setPublished(boolean published) {
        if (isPublished(published)) return (Issue)this;
        this.published = published ;
        propertyChanged("published", this.published);
        return (Issue)this;
    }

    public final boolean isPublished(boolean published) {
        return equals(this.published, published);
    }

    private transient PublishedModel publishedModel;

    public PublishedModel getPublishedModel() {
        if (publishedModel == null) publishedModel = createPublishedModel();
        return publishedModel;
    }

    protected PublishedModel createPublishedModel() { return new PublishedModel(); }

    protected class PublishedModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "Issue_published";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isPublished();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setPublished(value);
        }
        @Override
        public String getTooltip() { return "Issue is visible on the public homepage."; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- themes ---

    private java.util.List<java.lang.String> themes = new java.util.ArrayList<java.lang.String>();

    public final java.util.List<java.lang.String> getThemes() {
        return new java.util.ArrayList<java.lang.String>(themes);
    }

    public final void setThemes(java.util.List<java.lang.String> themes) {
        if (themes == null) throw new IllegalArgumentException("null is not allowed");
        if (this.themes.equals(themes)) return;
        this.themes = new java.util.ArrayList<java.lang.String>(themes);
        propertyChanged("themes", this.themes);
    }

    public final boolean containsTheme(java.lang.String theme) {
        return themes.contains(theme);
    }


    // --- update properties by map ---

    public void updateProperties(Map props) {
        projectId = (String) props.get("projectId");
        storyId = (String) props.get("storyId");
        number  = (Integer) props.get("number");
        type  = (java.lang.String) props.get("type");
        String dateAsString = (String) props.get("date");
        date  =  dateAsString == null ? null : new ilarkesto.core.time.DateAndTime(dateAsString);
        creatorId = (String) props.get("creatorId");
        label  = (java.lang.String) props.get("label");
        description  = (java.lang.String) props.get("description");
        statement  = (java.lang.String) props.get("statement");
        issuerName  = (java.lang.String) props.get("issuerName");
        issuerEmail  = (java.lang.String) props.get("issuerEmail");
        String acceptDateAsString = (String) props.get("acceptDate");
        acceptDate  =  acceptDateAsString == null ? null : new ilarkesto.core.time.Date(acceptDateAsString);
        urgent  = (Boolean) props.get("urgent");
        severity  = (Integer) props.get("severity");
        ownerId = (String) props.get("ownerId");
        String fixDateAsString = (String) props.get("fixDate");
        fixDate  =  fixDateAsString == null ? null : new ilarkesto.core.time.Date(fixDateAsString);
        String closeDateAsString = (String) props.get("closeDate");
        closeDate  =  closeDateAsString == null ? null : new ilarkesto.core.time.Date(closeDateAsString);
        String suspendedUntilDateAsString = (String) props.get("suspendedUntilDate");
        suspendedUntilDate  =  suspendedUntilDateAsString == null ? null : new ilarkesto.core.time.Date(suspendedUntilDateAsString);
        affectedReleasesIds = (Set<String>) props.get("affectedReleasesIds");
        fixReleasesIds = (Set<String>) props.get("fixReleasesIds");
        published  = (Boolean) props.get("published");
        themes  = (java.util.List<java.lang.String>) props.get("themes");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("storyId", this.storyId);
        properties.put("number", this.number);
        properties.put("type", this.type);
        properties.put("date", this.date == null ? null : this.date.toString());
        properties.put("creatorId", this.creatorId);
        properties.put("label", this.label);
        properties.put("description", this.description);
        properties.put("statement", this.statement);
        properties.put("issuerName", this.issuerName);
        properties.put("issuerEmail", this.issuerEmail);
        properties.put("acceptDate", this.acceptDate == null ? null : this.acceptDate.toString());
        properties.put("urgent", this.urgent);
        properties.put("severity", this.severity);
        properties.put("ownerId", this.ownerId);
        properties.put("fixDate", this.fixDate == null ? null : this.fixDate.toString());
        properties.put("closeDate", this.closeDate == null ? null : this.closeDate.toString());
        properties.put("suspendedUntilDate", this.suspendedUntilDate == null ? null : this.suspendedUntilDate.toString());
        properties.put("affectedReleasesIds", this.affectedReleasesIds);
        properties.put("fixReleasesIds", this.fixReleasesIds);
        properties.put("published", this.published);
        properties.put("themes", this.themes);
    }

    public final java.util.List<scrum.client.project.Requirement> getRequirements() {
        return getDao().getRequirementsByIssue((Issue)this);
    }

    @Override
    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getDescription(), key)) return true;
        if (matchesKey(getStatement(), key)) return true;
        return false;
    }

}