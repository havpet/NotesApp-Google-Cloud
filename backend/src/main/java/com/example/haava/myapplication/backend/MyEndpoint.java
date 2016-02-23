/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.haava.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.apphosting.datastore.DatastoreV4;


import java.util.List;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.myapplication.haava.example.com",
    ownerName = "backend.myapplication.haava.example.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "saveNote")
    public MyBean sayHi(@Named("title") String title, @Named("text") String text) {
        MyBean response = new MyBean();

        response.setData("Success");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

        Entity notatInn = new Entity("Note");
        notatInn.setProperty("title", title);
        notatInn.setProperty("text", text);

        datastore.put(notatInn);
        memcache.put("cache", notatInn);

        return response;
    }

    /*public String[] getNotes() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Note");
        List<Entity> notesList = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(20));


    }*/

}
