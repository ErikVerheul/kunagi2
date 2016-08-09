package junit.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    scrum.client.project.WeekdaySelectorTest.class,
    scrum.client.wiki.WikiTest.class,
    scrum.server.ScrumServiceImplTest.class,
    scrum.server.common.BurndownChartTest.class,
    scrum.server.common.PdfTest.class,
    scrum.server.common.WikiToPdfConverterTest.class,
    scrum.server.project.HomepageUpdaterTest.class,
    scrum.server.sprint.SprintHistoryHelperTest.class,
    scrum.server.sprint.SprintReportHelperTest.class
})

public class TestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
