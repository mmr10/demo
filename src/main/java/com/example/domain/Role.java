package com.example.domain;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "role")
public class Role implements Serializable {


    public Role()
    {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(length = 50)
    private String name;

    public Role(String name)
    {
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
