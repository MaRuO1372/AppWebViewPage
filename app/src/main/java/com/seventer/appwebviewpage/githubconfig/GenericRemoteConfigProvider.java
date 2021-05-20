package com.seventer.appwebviewpage.githubconfig;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jayway.jsonpath.ReadContext;

import java.lang.reflect.Type;

public abstract class GenericRemoteConfigProvider  implements AppConfigProvider {
    protected ReadContext ctx;

    public abstract GenericRemoteConfigProvider loadConfig() throws Exception;

    protected abstract GenericRemoteConfigProvider loadConfig(String url);

    public boolean has(String path){
        try {
            Object val = ctx.read(path);
            return val != null;
        }catch (Exception e){
            return false;
        }
    }

    public String getString(String path){
        return ctx.read(path, String.class);
    }

    public String getString(String path, String def){
        try{
            return getString(path);
        }catch (Exception e){
            return def;
        }
    }

    public boolean getBoolean(String path){
        return ctx.read(path, Boolean.class);
    }

    public boolean getBoolean(String path, boolean def){
        try{
            return getBoolean(path);
        }catch (Exception e){
            return def;
        }
    }

    @Override
    public int getInt(String path, int def) {
        try{
            return ctx.read(path, Integer.class);
        }catch (Exception e){
            return def;
        }
    }

    public JsonArray getArray(String path){
        try{
            return ctx.read(path, JsonArray.class);
        }catch (Exception e){
            return new JsonArray();
        }
    }

    public Object getObject(String path, Class type){
        try{
            return ctx.read(path, type);
        }catch (Exception e){
            return null;
        }
    }

    public Object getAsGsonObject(String path, Type tt){
        try{
            Gson g = new Gson();
            return g.fromJson(ctx.read(path).toString(), (Type) tt);
        }catch (Exception e){
            return null;
        }
    }

    public String jsonString() {
        return ctx.jsonString();
    }
}