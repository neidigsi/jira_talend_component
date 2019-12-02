package de.odisys.talend.components.source;

import static java.util.Collections.singletonList;

import java.io.Serializable;
import java.util.List;

import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Assessor;
import org.talend.sdk.component.api.input.Emitter;
import org.talend.sdk.component.api.input.PartitionMapper;
import org.talend.sdk.component.api.input.PartitionSize;
import org.talend.sdk.component.api.input.Split;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import de.odisys.talend.components.service.OdiSysService;

@Version(1)
@Icon(value= Icon.IconType.STAR)
@PartitionMapper(name = "OdiSysInput")
@Documentation("")
public class OdiSysInputMapper implements Serializable {
    private final OdiSysInputMapperConfiguration configuration;
    private final OdiSysService service;
    private final RecordBuilderFactory recordBuilderFactory;

    public OdiSysInputMapper(@Option("configuration") final OdiSysInputMapperConfiguration configuration,
                             final OdiSysService service,
                             final RecordBuilderFactory recordBuilderFactory) {
        this.configuration = configuration;
        this.service = service;
        this.recordBuilderFactory = recordBuilderFactory;
    }

    @Assessor
    public long estimateSize() {
        return 1L;
    }

    @Split
    public List<OdiSysInputMapper> split(@PartitionSize final long bundles) {
        return singletonList(this);
    }

    @Emitter
    public OdiSysInputSource createWorker() { return new OdiSysInputSource(configuration, service, recordBuilderFactory); }
}