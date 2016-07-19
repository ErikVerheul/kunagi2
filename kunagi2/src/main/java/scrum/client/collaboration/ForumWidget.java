
package scrum.client.collaboration;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.gwt.client.ButtonWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.workspace.PagePanel;

/**
 *
 * @author erik
 */
public class ForumWidget extends AScrumWidget {

    /**
     *
     */
    public BlockListWidget<ForumSupport> list;

	@Override
	protected Widget onInitialization() {
		new RequestForumServiceCall(false).execute();

		list = new BlockListWidget<ForumSupport>(ForumItemBlock.FACTORY);
		list.setAutoSorter(ForumSupport.COMPARATOR);

		PagePanel page = new PagePanel();
		page.addHeader("Forum", new ButtonWidget(new CreateSubjectAction()));
		page.addSection(list);
		page.addSection(new UserGuideWidget(getLocalizer().views().forum(),
				getCurrentProject().getSubjects().size() < 5, getCurrentUser().getHideUserGuideForumModel()));
		return page;
	}

	@Override
	protected void onUpdate() {
		list.setObjects(getCurrentProject().getEntitiesWithComments());
		super.onUpdate();
	}

    /**
     *
     * @param entity
     * @return
     */
    public boolean select(ForumSupport entity) {
		if (entity == null) {
                    return false;
        }
		if (!list.contains(entity)) {
                    update();
        }
		return list.showObject(entity);
	}
}
