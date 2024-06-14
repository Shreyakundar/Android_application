package com.example.projectsem;

public class Customer {
    String cname;
    String cmble;
    String ccheckin;
    String ccheckout;
    String cperson;
    String cpackages;

    public Customer(String cname, String cmble, String ccheckin, String ccheckout, String cperson, String cpackages) {
        this.cname = cname;
        this.cmble = cmble;
        this.ccheckin = ccheckin;
        this.ccheckout = ccheckout;
        this.cperson = cperson;
        this.cpackages = cpackages;
    }

    public String getCname() {
        return cname;
    }

    public String getCmble() {
        return cmble;
    }

    public String getCcheckin() {
        return ccheckin;
    }

    public String getCcheckout() {
        return ccheckout;
    }

    public String getCperson() {
        return cperson;
    }

    public String getCpackages() {
        return cpackages;
    }
}
