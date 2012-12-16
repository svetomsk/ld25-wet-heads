package main;

import java.applet.Applet;
import java.applet.AudioClip;

import sun.audio.*;;

public class Sound {
	public static final Sound music = new Sound("/sound.wav");

	private AudioClip clip;

	private Sound(String name) {
		try 
		{
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}