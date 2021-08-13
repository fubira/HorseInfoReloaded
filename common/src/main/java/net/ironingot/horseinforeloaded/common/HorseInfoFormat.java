package net.ironingot.horseinforeloaded.common;

import java.util.ArrayList;
import java.util.List;

public class HorseInfoFormat {
  public static List<String> formatHorseStats(double health, double maxHealth, double speed, double jumpStrength, double jumpHeight) {
    List<String> stringArray = new ArrayList<String>();
    stringArray.add(String.format("HP: %.2f/%.2f", health, maxHealth));
    stringArray.add(String.format("SP: %.4f [%.1f(m/s)]", speed, speed * 43.0D));
    stringArray.add(String.format("JP: %.4f [%.1f(m)]", jumpStrength, jumpHeight));
    return stringArray;
  }

  public static String formatHorseNameWithRank(String name, String evaluateRank) {
    return name + " [" + evaluateRank + "]";
  }
}
