package net.ironingot.horseinforeloaded.common;

import java.util.ArrayList;
import java.util.List;

public class HorseInfoFormat {
  public static List<String> formatHorseStats(double health, double maxHealth, double speed, double jumpStrength, double jumpHeight) {
    List<String> stringArray = new ArrayList<String>();
    String colorMaxHealth;
    if (maxHealth >= 28) {
      colorMaxHealth = "§c";
    } else if (maxHealth >= 24) {
      colorMaxHealth = "§g";
    } else if (maxHealth < 20) {
      colorMaxHealth = "§r";
    } else {
      colorMaxHealth = "";
    }
    String colorHealth;
    if (health < 10) {
      colorHealth = "§r";
    } else if (health == maxHealth) {
      colorHealth = colorMaxHealth;
    } else {
      colorHealth = "";
    }
    stringArray.add(String.format("Health: " + colorHealth + "%.2f" +  " §w/ " + colorMaxHealth + "%.2f", health, maxHealth));
    String colorSpeed;
    speed = speed * 43.0D;
    if (speed >= 13) {
      colorSpeed = "§c";
    } else if (speed >= 11) {
      colorSpeed = "§g";
    } else if (speed < 9) {
      colorSpeed = "§r";
    } else {
      colorSpeed = "";
    }
    stringArray.add(String.format("Speed: " + colorSpeed + "%.1f(m/s)", speed));
    String colorJump;
    if (jumpHeight >= 5) {
      colorJump = "§c";
    } else if (jumpHeight >= 4) {
      colorJump = "§g";
    } else if (jumpHeight < 2.5) {
      colorJump = "§r";
    } else {
      colorJump = "";
    }
    stringArray.add(String.format("Jump: " + colorJump + "%.1f(m)", jumpHeight));
    return stringArray;
  }

  public static String formatHorseNameWithRank(String name, String evaluateRank) {
    return name + " [" + evaluateRank + "]";
  }
}
