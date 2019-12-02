package de.odisys.talend.components.datastore;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

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
public class BasicAuth implements Serializable {
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

    public BasicAuth(String url, String username, String password) {
        this.baseurl = url;
        this.username = username;
        this.password = password;
    }

    public BasicAuth(){}

    public String getAuthorizationHeader() {
        try {
            return "Basic " + Base64.getEncoder().encodeToString((this.username + ":" + this.password).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return baseurl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.baseurl = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}