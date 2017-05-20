package com.williamhill.pageobjects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 20/05/2017
 *
 * @author 44016257 - Amarnath Ettukullapati
 */
public class FairyLightObjects {

    private ArrayList<FairyLight> lights;
    private String[] listOfColours;

    //Contrructors

    /**
     * LightArray Constructor
     * Create a List of Lights in the order of RED,GREEN,WHITE for the amount of light requested with in the l param.
     *
     * @param l int which determines the inital size of the ArrayList (which generated Lights)
     */
    public FairyLightObjects(int l) {
        lights = new ArrayList<FairyLight>();
        listOfColours = new String[]{"Red", "Green", "White"};
        for (int i = 0; i < l; i++) {
            lights.add(new FairyLight(getColour(i)));
        }
    }

    /**
     * LightArray Constructor
     * Create a List of Lights in the order of the colourList param for the amount of light requested with in the l param.
     * @param l int which determines the inital size of the ArrayList (which generated Lights)
     * @param colourList this is a String array contain the order of the lights Colour.
     */
    public FairyLightObjects(int l, String[] colourList) {
        lights = new ArrayList<FairyLight>();
        listOfColours = colourList;
        for (int i = 0; i < l; i++) {
            lights.add(new FairyLight(getColour(i)));
        }
    }

    //GETTERS

    /**
     * This method returns a colour from the listOfColour Array which would be the order of the colours within the group.
     * For Example:
     *  listOfColour =[RED,White]
     *  and you have 5 lights
     *  1 = RED, 2 = White, 3 = RED, 4 = White, 5 = RED
     *
     * @param i an int which represent the location within a chain of light to help decide the colour it should be.
     * @return Returns a String contain the colour with the order.
     */
    public String getColour(int i) {
        return listOfColours[i % listOfColours.length];
    }

    /**
     * @return Returns the size of the LightArray.
     */
    public int getLightArraySize() {
        return lights.size();
    }

    /**
     * Finds a sub-set of Lights within the array of a certain colour.
     * This could be imporved and adapted to handle mutiple colours (So find all the RED & GREEN Lights)
     * and maybe instead of returning the poststion it return the objects so you could control all lights in one go for example
     * getColours("BLUE").switchLightOn();
     *
     * @param colour The Colour you want to search for in the LightArray List
     * @return Returns an Array of int of the postsion within the larger arry
     */
    public ArrayList<Integer> getColoursPos(String colour) {
        ArrayList<Integer> colourPos = new ArrayList<Integer>();
        //check if colourexitst reduces loop
        if (colourInList(colour)) {
            for (int i = 0; i < lights.size(); i++) {
                FairyLight li = lights.get(i);
                if (li.isThisColour(colour)) {
                    colourPos.add(i);
                }
            }
        }
        return colourPos;
    }

    /**
     * Check if the colour your search for exist with the Array.
     * Could cause problems if you somehow add a new colour light bulb without putting in the listOfColours.
     * However the current implmentation doesn't handle atm.
     * In this event we may loop the whole lights Array to check in the future.
     *
     *
     * @param colour the colour your searching for
     * @return Return True if it found the Colour you were searching for.
     */
    public boolean colourInList(String colour) {
        for (String col : listOfColours) {
            if (colour.toLowerCase().equals(col.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

//setters

    /**
     * changes the state of the light for a single set of colours
     * problem is if you wish to sync the colour to be all on or all of your require another function currently no implmented.
     *
     * @param colour String, the colour of the subset of lights you wish to change the state of.
     */
    public void switchColourLight(String colour) {
        for (int i : getColoursPos(colour)) {
            changeLight(i);
        }
    }

    /**
     * Changes the state of a certain light bulb outputting a msg to the cli of what light was change and when.
     * @param pos location with in the lights array to change state for.
     */
    public void changeLight(int pos) {
        if (pos < lights.size()) {
            FairyLight li = lights.get(pos);
            li.switchLight();
            System.out.println((new SimpleDateFormat("HH:mm:ss:SSS").format(new Date())) + " : Light " + (pos + 1) + " " + li.toString());
        }
    }

    /**
     * change all light to there current opposite state.
     * code isn't very elegant atm need to rewite it to control it via the object and not the posistion similiar to turnOffAllTheLights & turnOnAllTheLights
     */
    public void flickAllTheSwitch() {
        int i = 1;
        for (FairyLight li : lights) {
            changeLight(i);
            i++;
        }
    }

    /**
     * Turn off all with in the lights Array
     */
    public void turnOffAllTheLights() {
        for (FairyLight li : lights) {
            li.turnOffLight();
        }
    }

    /**
     * Turn on all with in the lights Array
     */
    public void turnOnAllTheLights() {
        for (FairyLight li : lights) {
            li.turnOnLight();
        }
    }


    //[Time in format hh:mm:ss:sss] : Light <number> <colour> <on/off>

    /**
     * List the current outputs of all the light within the array
     */
    public void listLights() {
        int i = 1;
        for (FairyLight li : lights) {
            System.out.println((new SimpleDateFormat("HH:mm:ss:SSS").format(new Date())) + " : Light " + (i) + " " + li.toString());
            i++;
        }
    }

}
