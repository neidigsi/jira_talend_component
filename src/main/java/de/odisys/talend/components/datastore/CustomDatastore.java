package de.odisys.talend.components.datastore;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

@DataStore("CustomDatastore")
@GridLayout({
        @GridLayout.Row({"baseurl"}),
        @GridLayout.Row({"username", "password"})
})
@Documentation("")
public class CustomDatastore implements Serializable {
    @Option
    @Documentation("")
    private String baseurl;

    @Option
    @Documentation("")
    private String username;

    @Option
    @Credential
    @Documentation("")
    private String password;

    public CustomDatastore(String url, String username, String password) {
        this.baseurl = url;
        this.username = username;
        this.password = password;
    }

    public CustomDatastore(){}

    public String getUrl() {
        return baseurl;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setBaseurl(String baseurl) { this.baseurl = baseurl; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}