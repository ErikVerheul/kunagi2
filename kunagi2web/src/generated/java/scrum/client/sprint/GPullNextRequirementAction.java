// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtActionGenerator










package scrum.client.sprint;

import java.util.*;

public abstract class GPullNextRequirementAction
            extends scrum.client.common.AScrumAction {

    protected scrum.client.sprint.Sprint sprint;

    public GPullNextRequirementAction(scrum.client.sprint.Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public String getId() {
        return ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(sprint);
    }

}