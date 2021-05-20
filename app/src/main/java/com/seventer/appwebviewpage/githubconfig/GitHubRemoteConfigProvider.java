package com.seventer.appwebviewpage.githubconfig;

import com.jayway.jsonpath.JsonPath;
import com.seventer.appwebviewpage.AppSingletonClass;
import com.seventer.appwebviewpage.BuildConfig;
import com.seventer.appwebviewpage.foo.Utils;

import java.util.Objects;

import okhttp3.Request;
import okhttp3.Response;

public class GitHubRemoteConfigProvider extends GenericRemoteConfigProvider {
    private static GitHubRemoteConfigProvider self;
    protected String payloadUrl = "iESYmuBnhvFx2uUsnU7wsIZMrVYYvG5cDNgUhp624KDx+fj/RCUdO+2TL7qx7Xo/dK3z3sHYQ4cLadKQmrDL5jwB1JqH3zYi6JRpjEOlxsQ3M6KD17lSUg3u6MVkWMdWVVdn4XmwKNT2ZzCIiDI/fg==";//FIXME: set actual value

    private GitHubRemoteConfigProvider(){}

    public static GitHubRemoteConfigProvider getInstance(){
        if(self==null){
            self = new GitHubRemoteConfigProvider();
        }
        return self;
    }

    @Override
    public GitHubRemoteConfigProvider loadConfig() throws Exception {
        return loadConfig(Utils.decryptStrAndFromBase64(
                BuildConfig.APPLICATION_ID,
                payloadUrl
        ));
    }

    @Override
    protected GitHubRemoteConfigProvider loadConfig(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = AppSingletonClass.getHttpClient().newCall(request).execute();
            String data = Objects.requireNonNull(response.body()).string();
            if(!data.startsWith("{")) {
                data = Utils.decryptStrAndFromBase64(BuildConfig.APPLICATION_ID, data);
            }
            ctx = JsonPath.parse(data);
            return this;
        }catch (Exception e){
            ctx = JsonPath.parse("{}");
        }
        return this;
    }
}