# HorseInfoReloaded

A client-side Minecraft mod that displays detailed information about horses and other tameable animals.

**Supported Platforms:** Fabric & NeoForge
**Minecraft Version:** 26.1.2
**Mod Version:** 2.8

## Features

### Horse Information Display

- **Name** - Custom or default name
- **Health** - Current/Max HP
- **Speed** - Movement speed in m/s
- **Jump Height** - Jump capability in meters
- **Owner** - Player who tamed the animal
- **Rank** - Performance rating (G to LEGEND)

Horses with exceptional abilities are displayed with color-coded information windows based on their rank.

![screenshot](https://i.imgur.com/hQ7fqVn.jpg)

### Supported Animals

- **Horse** (all variants)
- **Donkey**
- **Mule**
- **Skeleton Horse**
- **Zombie Horse**
- **Wolf** (Dog)
- **Cat**
- **Parrot**
- **Llama**
- **Camel**

## Usage

1. Install the mod to your mods directory
2. Launch Minecraft
3. Press `H` key to toggle the mod ON/OFF
4. Look at a horse or supported animal to see its information

## Configuration

The mod can be enabled/disabled using the `H` key (default).
Configuration file location:
- **Fabric:** `.minecraft/config/horseinforeloaded.json`
- **NeoForge:** `.minecraft/config/horseinforeloaded-client.toml`

## Download

[Releases](https://github.com/fubira/HorseInfoReloaded/releases)

## Building from Source

```bash
git clone https://github.com/fubira/HorseInfoReloaded.git
cd HorseInfoReloaded
./gradlew build
```

Built JAR file: `build/libs/HorseInfoReloaded-26.1.2-2.8.jar`

## License

MIT License - See [LICENSE.md](LICENSE.md) for details
