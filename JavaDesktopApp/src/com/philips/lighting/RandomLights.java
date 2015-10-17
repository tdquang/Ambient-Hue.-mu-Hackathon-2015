package com.philips.lighting;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.philips.lighting.Controller;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class RandomLights extends TimerTask {
    private static final int MAX_HUE=65535;
	List<PHLight> lights;
	PHBridge bridge;
	String group;
	boolean isGroup;
	String model;
	public RandomLights(List<PHLight> newLights, PHBridge newBridge){
		lights = newLights;
		bridge = newBridge;
		isGroup = false;
	}
	public RandomLights(String newGroup, PHBridge newBridge, String newModel){
		group = newGroup;
		bridge = newBridge;
		model = newModel;
		isGroup = true;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		PHLightState lightState = new PHLightState();
		int[] rgb = this.getRGBs();

//        lightState.setHue(rand.nextInt(MAX_HUE));
        if(isGroup){
    		float xy[] = PHUtilities.calculateXYFromRGB(rgb[0],rgb[1],rgb[2],model);
    		lightState.setX(xy[0]);
    		lightState.setY(xy[1]);
        	bridge.setLightStateForGroup(group, lightState);
        }else{
        	for (PHLight light : lights) {
        		float xy[] = PHUtilities.calculateXYFromRGB(rgb[0],rgb[1],rgb[2],light.getModelNumber());
        		lightState.setX(xy[0]);
        		lightState.setY(xy[1]);
        		bridge.updateLightState(light, lightState); // If no bridge response is required then use this simpler form.
        	}
        }
	}
	
	public int[] getRGBs(){
		Random rand = new Random();
		int[] x = {rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)};
		return x;
	}
}
