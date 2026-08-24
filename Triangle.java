import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle{
    
    public static int VERTICES=3;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle(){
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
        isVisible = false;
    }

    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the triangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the triangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the triangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the triangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the triangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the triangle vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }

    /*
     * Draw the triangle with current specifications on screen.
     */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width/2), xPosition - (width/2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

    /*
     * Erase the triangle on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Return the approximate area of this triangle, based on its
     * Return the area in square pixels
     */
    public double area(){
        return (width * height) / 2.0;
    }
    
    /**
     * Turn this triangle into an equilateral triangle of approximately
     * the same area as it currently has
     */
    public void equilateral(){
        double currentArea = area();
        double side = Math.sqrt((4 * currentArea) / Math.sqrt(3));
        int newWidth = (int) Math.round(side);
        int newHeight = (int) Math.round(side * Math.sqrt(3)/2);
        changeSize(newHeight, newWidth);
    }
    
    /**
     * Make the triangle "fall" while walking sideways: on each step it
     * moves a bit horizontally (right if times is positive, left if
     * negative) and a bit downward, abs(times) times
     */
    public void walk(int times){
        int direction;
        if (times < 0) {
            direction = -1;
        } else {
            direction = 1;
        }
        int steps = Math.abs(times);
        
        for (int i = 0; i < steps; i++){
            moveHorizontal(direction * 10);
            moveVertical(10);
        }
    }
    
    /**
     * Create a new triangle with a given color, width and height,
     * at the default position
     * color -> the initial color
     * width -> the initial width
     * height -> the initial height
     */
    public Triangle(String color, int width, int height){
        this.height = height;
        this.width = width;
        xPosition = 140;
        yPosition = 15;
        this.color = color;
        isVisible = false;
    }
    
    /**
     * Check whether this triangle has a bigger area than another one.
     * other -> the triangle to compare against
     * Return true if this triangle's area is greater than other's
     */
    public boolean isBigger(Triangle other){
        return this.area() > other.area();
    }
}

