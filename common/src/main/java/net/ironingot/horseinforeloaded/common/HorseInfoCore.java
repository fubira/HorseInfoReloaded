package net.ironingot.horseinforeloaded.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.ironingot.horseinforeloaded.common.playername.PlayerNameManager;

public class HorseInfoCore {
  public static final Logger logger = LogManager.getLogger();
  public static final String modId ="horseinforeloaded";
  public static final PlayerNameManager playerNameManager = new PlayerNameManager();
}
