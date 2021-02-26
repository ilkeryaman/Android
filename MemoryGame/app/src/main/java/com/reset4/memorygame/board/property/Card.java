package com.reset4.memorygame.board.property;

/**
 * Created by eilkyam on 27.12.2017.
 */

public class Card{
    private String name;
    private int resource;

    public Card(String name, int resource){
        setName(name);
        setResource(resource);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getResource() {
        return resource;
    }

    private void setResource(int resource) {
        this.resource = resource;
    }


}
