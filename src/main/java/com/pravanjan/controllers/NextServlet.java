package com.pravanjan.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

    // [START example]
    //@SuppressWarnings("serial")
    @WebServlet(name = "nextServlet", urlPatterns = "/next")
    public class NextServlet extends HttpServlet {

        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            PrintWriter out = resp.getWriter();
            out.println("This is next servlert");
        }
    }

