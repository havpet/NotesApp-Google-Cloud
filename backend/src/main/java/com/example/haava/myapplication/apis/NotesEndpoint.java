package com.example.haava.myapplication.apis;

import com.example.haava.myapplication.Models.Notes;
import com.example.haava.myapplication.backend.MyBean;
import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;


@Api(
    name = "myApi",
    version = "v1",
    namespace = @ApiNamespace(
            ownerDomain = "backend.myapplication.haava.example.com.apis",
            ownerName = "backend.myapplication.haava.example.com.apis",
            packagePath=""
    )
)


public class NotesEndpoint {

    @ApiMethod(name = "GET")
    public MyBean listNotes(final User user) {
        MyBean response = new MyBean();
        return response;
    }

    @ApiMethod(httpMethod = "GET")
    public final Notes getNotes(@Named("id") final Long id, final User user) throws ServiceException {
        return null;
    }

}
