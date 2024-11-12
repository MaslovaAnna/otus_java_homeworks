package ru.otus.java.classworks.cw12;

import java.util.Objects;

public class Box extends Object {
    private int size;
    private int color;

    public Box(int size, int color){
        this.size = size;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Box size = " + size + " color = " + color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Box that = (Box) obj;
        return (that.color == this.color) && (that.size == this.size);
    }

    @Override
    public int hashCode() {
        //return Objects.hash(size, color);
        return super.hashCode();
    }
}
