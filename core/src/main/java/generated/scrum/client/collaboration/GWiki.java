// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.collaboration;

import scrum.client.collaboration.Wiki;

public abstract class GWiki {

    protected final static ilarkesto.core.logging.Log log = ilarkesto.core.logging.Log.get(Wiki.class);

    public abstract void initialize();

    protected scrum.client.Dao dao;

    protected scrum.client.project.Project project;

    protected scrum.client.files.Uploader uploader;

    @Override
    public String toString() {
        return "Wiki";
    }

}

