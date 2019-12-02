package de.odisys.talend.components.output;

import java.io.Serializable;

import de.odisys.talend.components.dataset.SearchQuery;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "dataset" })
})
@Documentation("TODO fill the documentation for this configuration")
public class JiraOutputOutputConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private SearchQuery dataset;

    public SearchQuery getDataset() {
        return dataset;
    }

    public JiraOutputOutputConfiguration setDataset(SearchQuery dataset) {
        this.dataset = dataset;
        return this;
    }
}