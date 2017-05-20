package com.williamhill.pageobjects;

/**
 * 20/05/2017
 *
 * @author 44016257 - Amarnath Ettukullapati
 */

public class FairyLight {


    private boolean lightStatus;
    private String colour;

    public FairyLight() {
        lightStatus = false;
        this.colour = "white";
    }

    /**
     * Light constructor
     * Creates a Light Object which is switched off and bulb colour is defined by colour param.
     *
     * @param colour Colour of the Bulb.
     */
    public FairyLight(String colour) {
        lightStatus = false;
        this.colour = colour;
    }

    /**
     * Light constructor
     * Creates a Light Object bulb colour is defined by colour param and the state of bulb is also a param.
     *
     * @param colour Colour of the Bulb.
     * @param state  set the Bulbs state of ON or OFF
     */
    public FairyLight(String colour, boolean state) {
        lightStatus = state;
        this.colour = colour;
    }




    public String getColour() {
        return colour;
    }

    public void setColour(String s) {
        colour = s.toLowerCase();
    }


    public boolean isLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(boolean lightStatus) {
        this.lightStatus = lightStatus;
    }


    /**
     * @return Return the LightBulb Object as A String example. Red ON
     * @see Object
     */
    @Override
    public String toString() {
        return colour + " " + getStrlightStatus();
    }



    /**
     * @param col String giving the name of colour.
     * @return Returns a Boolean (True = they are the same colour / False = not the same colour)
     */
    public boolean isThisColour(String col) {
        return colour.toLowerCase().equals(col.toLowerCase());
    }

    /**
     * @param li Light Object  to compare the colour of the bulb objects.
     * @return Retur;ns a Boolean (True = they are the same colour / False = not the same colour)
     */
    public boolean isThisColour(FairyLight li) {
        return colour.toLowerCase().equals(li.getColour().toLowerCase());
    }

    /**
     * @return Returns a String with the text version of the boolean being ON or OFF
     */
    public String getStrlightStatus() {
        return (lightStatus) ? "On" : "Off";
    }




    /**
     * Turn OFF the LightBulb
     */
    public void turnOffLight() {
        setLightStatus(false);
    }

    /**
     *Turn ON the LightBulb
     */
    public void turnOnLight() {
        setLightStatus(true);
    }

    /**
     * Flick the lightbulb state ON if it OFF or OFF if its ON
     */
    public void switchLight() {
        if (lightStatus) {
            turnOffLight();
        } else {turnOnLight();}
    }
}