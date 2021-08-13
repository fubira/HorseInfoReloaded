package net.ironingot.horseinforeloaded.common;

import java.awt.Color;

public class HorseInfoStats {
  public static double calcJumpHeight(double strength) {
    double yVelocity = strength;
    double jumpHeight = 0.0d;

    while (yVelocity > 0.0D) {
      jumpHeight += yVelocity;
      yVelocity -= 0.08D;
      yVelocity *= 0.98D;
    }

    return Math.floor(jumpHeight * 10.0D) / 10.0D ;
  }

  public static double calcEvaluateValue(double paramSpeed, double jumpHeight) {
    double jumpRating = Math.floor(jumpHeight * 2.0D) / (2.0D * 5.0D);

    final double speedHeavy = 10.0D;
    final double heightHeavy = 1.0D;

    final double valueMax = 0.3375D * speedHeavy + 1.0D * heightHeavy;
    double value = (paramSpeed * speedHeavy) + jumpRating * heightHeavy;
    return value / valueMax;
  }

  public static String calcEvaluateRankString(double paramSpeed, double jumpHeight) {
    double horseEvaluate = calcEvaluateValue(paramSpeed, jumpHeight);

    final String [] rankString = {
      "G", "G", "G",
      "F", "F", "F",
      "E", "E", "E",
      "D", "D", "D",
      "C", "C+", "C++",
      "B", "B+", "B++",
      "A", "A+", "A++",
      "S", "S+", "S++",
      "LEGEND"
    };

    double rate = horseEvaluate * 2.0D - 1.0;

    int pt = (int)(rate * rankString.length);
    if (pt >= rankString.length) {
      return rankString[rankString.length-1];
    }
    if (pt < 0) {
      return rankString[0];
    }

    return rankString[pt];
  }

  public static Color calcEvaluateRankColor(double paramSpeed, double jumpHeight) {
    double horseEvaluate = calcEvaluateValue(paramSpeed, jumpHeight);

    final Color [] rankColor = {
      Color.BLACK, Color.BLACK, Color.BLACK,
      Color.BLACK, Color.BLACK, Color.BLACK,
      Color.BLACK, Color.BLACK, Color.BLACK,
      Color.BLACK, Color.BLACK, Color.BLACK,
      Color.BLACK, Color.BLACK, Color.BLACK,
      new Color(0x55, 0x55, 0xFF), new Color(0x55, 0x55, 0xFF), new Color(0x00, 0xAA, 0xFF),
      new Color(0x55, 0xFF, 0xFF), new Color(0x55, 0xFF, 0x55), new Color(0xFF, 0xFF, 0x55),
      new Color(0xFF, 0xAA, 0x00), new Color(0xFF, 0x55, 0x55), new Color(0xFF, 0x55, 0xFF),
      new Color(0xFF, 0xCC, 0xFF)
    };
    double rate = horseEvaluate * 2.0D - 1.0;
    int pt = (int)(rate * rankColor.length);
    if (pt >= rankColor.length) {
      return rankColor[rankColor.length-1];
    }
    if (pt < 0) {
      return rankColor[0];
    }
    return rankColor[pt];
  }
}
