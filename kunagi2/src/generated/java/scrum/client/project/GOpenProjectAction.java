// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtActionGenerator










package scrum.client.project;

import static ilarkesto.core.logging.ClientLog.DEBUG;
import java.util.*;

public abstract class GOpenProjectAction
            extends scrum.client.common.AScrumAction {

    protected scrum.client.project.Project project;

    public GOpenProjectAction(scrum.client.project.Project project) {
        this.project = project;
        DEBUG("GOpenProjectAction:Opening project: " + project.getShortDescription());
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public String getId() {
        DEBUG("GOpenProjectAction:getId: " + ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(project));
        return ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(project);
    }

}