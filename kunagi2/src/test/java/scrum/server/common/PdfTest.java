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
package scrum.server.common;

import ilarkesto.base.StrExtend;
import ilarkesto.base.UtlExtend;
import ilarkesto.core.time.Date;
import ilarkesto.integration.itext.PdfBuilder;
import ilarkesto.junit.AjunitTest;
import ilarkesto.logging.Log;
import java.io.File;
import java.util.Arrays;
import junit.scrum.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import scrum.server.calendar.CalendarPdfCreator;
import scrum.server.collaboration.Wikipage;
import scrum.server.collaboration.WikipagePdfCreator;
import scrum.server.impediments.Impediment;
import scrum.server.impediments.ImpedimentListPdfCreator;
import scrum.server.issues.BugListPdfCreator;
import scrum.server.issues.IdeaListPdfCreator;
import scrum.server.issues.Issue;
import scrum.server.project.ProductBacklogPdfCreator;
import scrum.server.project.Project;
import scrum.server.project.QualityBacklogPdfCreator;
import scrum.server.project.Requirement;
import scrum.server.release.ReleaseHistoryPdfCreator;
import scrum.server.release.ReleasePlanPdfCreator;
import scrum.server.risks.RiskListPdfCreator;
import scrum.server.sprint.Sprint;
import scrum.server.sprint.SprintBacklogPdfCreator;
import scrum.server.sprint.SprintReportPdfCreator;
import scrum.server.sprint.Task;

/**
 *
 *
 */
public class PdfTest extends AjunitTest {

    private static final Log LOG = Log.get(PdfTest.class);

    static Project project;

    /**
     *
     */
    @BeforeClass
    public static void initglobal() {
        TestUtil.initialize();
        TestUtil.getApp().getSystemConfig().setUrl("http://localhost/kunagi");
        project = TestUtil.createProject(TestUtil.getDuke());
    }

    /**
     *
     */
    @Test
    public void calendar() {
        Project projectLocal = TestUtil.createProject(TestUtil.getDuke());

        TestUtil.createSimpleEvent(projectLocal, 0);
        TestUtil.createSimpleEvent(projectLocal, 10);
        TestUtil.createSimpleEvent(projectLocal, 20);

        createPdf(new CalendarPdfCreator(projectLocal, Date.today(), Date.inDays(30)));
    }

    /**
     *
     */
    @Test
    public void riskList() {
        Project projectLocal = TestUtil.createProject();

        TestUtil.createRisk(projectLocal, 1);
        TestUtil.createRisk(projectLocal, 2);
        TestUtil.createRisk(projectLocal, 3);
        TestUtil.createRisk(projectLocal, 4);
        TestUtil.createRisk(projectLocal, 5);

        createPdf(new RiskListPdfCreator(projectLocal));
    }

    /**
     *
     */
    @Test
    public void impedimentList() {
        Project ProjectLocal = TestUtil.createProject();

        TestUtil.createImpediment(ProjectLocal, 1);
        TestUtil.createImpediment(ProjectLocal, 2);

        Impediment imp3 = TestUtil.createImpediment(ProjectLocal, 3);
        imp3.setSolution(StrExtend.generateRandomParagraphs(2));

        Impediment imp4 = TestUtil.createImpediment(ProjectLocal, 4);
        Requirement req1 = TestUtil.createRequirement(ProjectLocal, 1);
        TestUtil.createTask(req1, 1, 3, 0).setImpediment(imp4);
        TestUtil.createTask(req1, 2, 1, 0).setImpediment(imp4);

        TestUtil.createImpediment(ProjectLocal, 5);

        createPdf(new ImpedimentListPdfCreator(ProjectLocal));
    }

    /**
     *
     */
    @Test
    public void sprintBacklog() {
        Project project = TestUtil.createProject();

        Sprint sprint = TestUtil.createSprint(project, Date.today());
        project.setCurrentSprint(sprint);

        Requirement req1 = TestUtil.createRequirement(project, 1);
        req1.setEstimatedWork(3f);
        req1.setDirty(false);
        TestUtil.createTasks(req1, UtlExtend.randomInt(1, 10));
        req1.setSprint(sprint);

        Requirement req2 = TestUtil.createRequirement(project, 2);
        req2.setEstimatedWork(0.5f);
        req2.setDirty(false);
        TestUtil.createTasks(req1, UtlExtend.randomInt(0, 2));
        TestUtil.createTask(req2, 666, 1, 0).setImpediment(TestUtil.createImpediment(project, 666));
        req2.setSprint(sprint);

        createPdf(new SprintBacklogPdfCreator(project));
    }

    /**
     *
     */
    @Test
    public void productBacklog() {
        Project projectLocal = TestUtil.createProject();

        TestUtil.createRequirement(projectLocal, 1).setEstimatedWork(3f);
        TestUtil.createRequirement(projectLocal, 2);

        createPdf(new ProductBacklogPdfCreator(projectLocal));
    }

    /**
     *
     */
    @Test
    public void qualityBacklog() {
        Project projectLocal = TestUtil.createProject();

        TestUtil.createQuality(projectLocal, 1);
        TestUtil.createQuality(projectLocal, 2);
        TestUtil.createQuality(projectLocal, 3);

        createPdf(new QualityBacklogPdfCreator(projectLocal));
    }

    /**
     *
     */
    @Test
    public void bugList() {
        Project projectLocal = TestUtil.createProject();

        TestUtil.createBug(projectLocal, 1);
        TestUtil.createBug(projectLocal, 2).setOwner(TestUtil.getDuke());

        Issue bug3 = TestUtil.createBug(projectLocal, 3);
        bug3.setOwner(TestUtil.getDuke());
        bug3.setFixDate(Date.beforeDays(2));

        createPdf(new BugListPdfCreator(projectLocal));
    }

    /**
     *
     */
    @Test
    public void ideaList() {
        Project projectLocal = TestUtil.createProject();

        TestUtil.createIssue(projectLocal, 1).setAcceptDate(Date.today());
        TestUtil.createIssue(projectLocal, 2).setAcceptDate(Date.today());
        TestUtil.createIssue(projectLocal, 3).setAcceptDate(Date.today());

        createPdf(new IdeaListPdfCreator(projectLocal));
    }

    /**
     *
     */
    @Test
    public void releasePlan() {
        Project projectLocal = TestUtil.createProject();

        TestUtil.createRelease(projectLocal, 1);
        TestUtil.createRelease(projectLocal, 2);
        TestUtil.createRelease(projectLocal, 3);

        createPdf(new ReleasePlanPdfCreator(projectLocal));
    }

    /**
     *
     */
    @Test
    public void releaseHistory() {
        Project projectLocal = TestUtil.createProject();

        TestUtil.createRelease(projectLocal, 1).setReleased(true);
        TestUtil.createRelease(projectLocal, 2).setReleased(true);
        TestUtil.createRelease(projectLocal, 3).setReleased(true);

        createPdf(new ReleaseHistoryPdfCreator(projectLocal));
    }

    /**
     *
     */
    @Test
    public void sprintReport() {
        Project projectLocal = TestUtil.createProject();
        projectLocal.addProductOwners(Arrays.asList(TestUtil.createUser("Cartman")));
        projectLocal.addScrumMasters(Arrays.asList(TestUtil.createUser("Homer")));
        projectLocal.addTeamMembers(Arrays.asList(TestUtil.createUser("Alfred"), TestUtil.createUser("Duke")));

        Sprint sprint = new Sprint();
        sprint.setProject(projectLocal);
        sprint.setEnd(Date.beforeDays(3));
        sprint.setBegin(sprint.getEnd().addDays(-30));
        sprint.setLabel("Productivity Boost Sprint ä ü ö ß Mirosław");
        sprint.setGoal(StrExtend.generateRandomSentence(3, 15));
        sprint.setPlanningNote(StrExtend.generateRandomParagraphs(2));
        sprint.setReviewNote(StrExtend.generateRandomParagraphs(2));
        sprint.setRetrospectiveNote(StrExtend.generateRandomParagraphs(2));
        sprint.setVelocity(666f);

        Requirement req1 = TestUtil.createRequirement(sprint, 1, 0.5f);
        Task tsk1 = TestUtil.createTask(req1, 1, 0, 1);
        tsk1.setDescription(StrExtend.generateRandomParagraphs(3));
        req1.setClosed(true);

        Requirement req2 = TestUtil.createRequirement(sprint, 2, 1f);
        Requirement req3 = TestUtil.createRequirement(sprint, 3, 5f);
        TestUtil.createTask(req2, 2, 1, 1);
        TestUtil.createTask(req3, 3, 2, 2);

        sprint.close();

        createPdf(new SprintReportPdfCreator(sprint));
    }

    /**
     *
     */
    @Test
    public void wikipage() {
        Wikipage wikipage = new Wikipage();
        wikipage.setName("wikipage");
        wikipage.setText("= Test Wiki Page =\n\nTest wiki page.\nTest wiki page. Test wiki page. Test wiki page. Test wiki page. Test wiki page. Test wiki page. Test wiki page. Test wiki page. Test wiki page. ");
        createPdf(new WikipagePdfCreator(wikipage));
    }

    private void createPdf(APdfCreator creator) {
        PdfBuilder pdf = new PdfBuilder();
        creator.build(pdf);
        File file = new File(OUTPUT_DIR + "/" + creator.getFilename() + ".pdf");
        LOG.debug("Writing PDF:", file);
        pdf.write(file);
    }

}
