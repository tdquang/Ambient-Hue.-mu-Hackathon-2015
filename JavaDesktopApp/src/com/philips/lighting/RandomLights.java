package com.philips.lighting;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.philips.lighting.Controller;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class RandomLights extends TimerTask {
    private static final int MAX_HUE=65535;
	List<PHLight> lights;
	PHBridge bridge;
	public RandomLights(List<PHLight> newLights, PHBridge newBridge){
		lights = newLights;
		bridge = newBridge;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		PHLightState lightState = new PHLightState();
        lightState.setHue(rand.nextInt(MAX_HUE));
		for (PHLight light : lights) {
            bridge.updateLightState(light, lightState); // If no bridge response is required then use this simpler form.
        }
	}
}
