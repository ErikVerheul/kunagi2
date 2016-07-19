
package scrum.client.risks;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.workspace.PagePanel;

/**
 *
 * @author erik
 */
public class RiskListWidget extends AScrumWidget {

    /**
     *
     */
    public BlockListWidget<Risk> list;

	@Override
	protected Widget onInitialization() {
		new RequestRisksServiceCall().execute();

		list = new BlockListWidget<Risk>(RiskBlock.FACTORY);
		list.setAutoSorter(Risk.PRIORITY_COMPARATOR);

		PagePanel page = new PagePanel();
		page.addHeader("Risk List", new ButtonWidget(new CreateRiskAction()));
		page.addSection(Gwt.createFlowPanel(list, Gwt.createSpacer(1, 10),
			ScrumGwt.createPdfLink("Downlad as PDF", "riskList", "")));
		page.addSection(new UserGuideWidget(getLocalizer().views().risks(), getCurrentProject().getRisks().size() < 5,
				getCurrentUser().getHideUserGuideRisksModel()));
		return page;
	}

	@Override
	protected void onUpdate() {
		list.setObjects(getCurrentProject().getRisks());
		super.onUpdate();
	}

    /**
     *
     * @param risk
     * @return
     */
    public boolean select(Risk risk) {
		if (!list.contains(risk)) {
                    update();
        }
		return list.showObject(risk);
	}

}
