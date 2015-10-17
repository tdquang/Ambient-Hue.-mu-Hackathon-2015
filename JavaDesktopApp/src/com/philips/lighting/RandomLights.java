package com.philips.lighting;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
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
	Robot robot;
	public RandomLights(List<PHLight> newLights, PHBridge newBridge){
		lights = newLights;
		bridge = newBridge;
		isGroup = false;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public RandomLights(String newGroup, PHBridge newBridge, String newModel){
		group = newGroup;
		bridge = newBridge;
		model = newModel;
		isGroup = true;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		PHLightState lightState = new PHLightState();

//        lightState.setHue(rand.nextInt(MAX_HUE));
        if(isGroup){
        	int[] rgb = this.getRGB();
    		float xy[] = PHUtilities.calculateXYFromRGB(rgb[0],rgb[1],rgb[2],model);
    		lightState.setX(xy[0]);
    		lightState.setY(xy[1]);
        	bridge.setLightStateForGroup(group, lightState);
        }else{
    		int[][] rgbs = this.update();
        	for (int i = 0; i<lights.size(); i++){
        		int[] rgb = rgbs[i];
        		PHLight light = lights.get(i);
        		lightState.isOn();
        		float xy[] = PHUtilities.calculateXYFromRGB(rgb[0],rgb[1],rgb[2],light.getModelNumber());
        		lightState.setX(xy[0]);
        		lightState.setY(xy[1]);
        		int grey = (rgb[0]+rgb[1]+rgb[2])/3;
        		if (grey<10){
        			lightState.setOn(false);
        		}else{
        			if(!light.getLastKnownLightState().isOn()){ lightState.setOn(true); }
        		}
        		lightState.setBrightness(grey);
        		bridge.updateLightState(light, lightState); // If no bridge response is required then use this simpler form.
        	}
        }
	}
	
	public int[][] getRGBs(){
		Random rand = new Random();
		int[] y = {rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)};
		int[][] x = {y,y,y,y,y};
		return x;
	}
	public int[] getRGB(){
		Random rand = new Random();
		int[] x = {rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)};
		return x;
	}
	
	public int[][] update(){
		int[][] intList = new int[5][3];
		try {
			robot = new Robot();
			Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
			int total = (bufferedImage.getHeight()/4 * bufferedImage.getWidth()/8)/4;
			int red = 0, green = 0, blue = 0;
			for (int i = 0; i < bufferedImage.getHeight()/4; i=i+2){
				for (int j = 0; j < bufferedImage.getWidth()/8; j=j+2){
					int clr = bufferedImage.getRGB(j,i);
					red += (clr >> 16) & 0xFF;
					green += (clr >> 8) & 0xFF;
					blue += clr & 0xFF;
				}
			}

			red = red / total;
			green = green / total;
			blue = blue / total;
			intList[0][0] = red;
			intList[0][1] = green;
			intList[0][2] = blue;



			red = 0; green = 0; blue = 0;
			for (int i = bufferedImage.getHeight()*3/4; i < bufferedImage.getHeight(); i=i+2){
				for (int j = 0; j < bufferedImage.getWidth()/8; j=j+2){
					int clr = bufferedImage.getRGB(j,i);
					red += (clr >> 16) & 0xFF;
					green += (clr >> 8) & 0xFF;
					blue += clr & 0xFF;
				}
			}

			red = red / total;
			green = green / total;
			blue = blue / total;
			intList[1][0] = red;
			intList[1][1] = green;
			intList[1][2] = blue;

			red = 0; green = 0; blue = 0;
			for (int i = 0; i < bufferedImage.getHeight()/4; i=i+2){
				for (int j = bufferedImage.getWidth()*7/8; j < bufferedImage.getWidth(); j=j+2){
					int clr = bufferedImage.getRGB(j,i);
					red += (clr >> 16) & 0xFF;
					green += (clr >> 8) & 0xFF;
					blue += clr & 0xFF;
				}
			}

			red = red / total;
			green = green / total;
			blue = blue / total;
			intList[2][0] = red;
			intList[2][1] = green;
			intList[2][2] = blue;


			red = 0; green = 0; blue = 0;
			for (int i = bufferedImage.getHeight()*3/4; i < bufferedImage.getHeight(); i=i+2){
				for (int j = bufferedImage.getWidth()*7/8; j < bufferedImage.getWidth(); j=j+2){
					int clr = bufferedImage.getRGB(j,i);
					red += (clr >> 16) & 0xFF;
					green += (clr >> 8) & 0xFF;
					blue += clr & 0xFF;
				}
			}

			red = red / total;
			green = green / total;
			blue = blue / total;
			intList[3][0] = red;
			intList[3][1] = green;
			intList[3][2] = blue;

			red = 0; green = 0; blue = 0;
			for (int i = 0; i < bufferedImage.getHeight()/4; i=i+2){
				for (int j = bufferedImage.getWidth()*7/16; j < bufferedImage.getWidth()*9/16; j=j+2){
					int clr = bufferedImage.getRGB(j,i);
					red += (clr >> 16) & 0xFF;
					green += (clr >> 8) & 0xFF;
					blue += clr & 0xFF;
				}
			}

			red = red / total;
			green = green / total;
			blue = blue / total;
			intList[4][0] = red;
			intList[4][1] = green;
			intList[4][2] = blue;

		} catch (AWTException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return intList;
	}
}
