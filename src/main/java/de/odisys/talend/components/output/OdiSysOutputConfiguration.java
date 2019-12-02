package de.odisys.talend.components.output;

import java.io.Serializable;

import de.odisys.talend.components.dataset.CustomDataset;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "dataset" })
})
@Documentation("TODO fill the documentation for this configuration")
public class OdiSysOutputConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private CustomDataset dataset;

    public CustomDataset getDataset() {
        return dataset;
    }

    public OdiSysOutputConfiguration setDataset(CustomDataset dataset) {
        this.dataset = dataset;
        return this;
    }
}