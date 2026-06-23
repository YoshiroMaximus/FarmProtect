# <img src="https://github.com/user-attachments/assets/5d33a48a-ab7a-4147-b146-241f882acaf9" width="50"/> FarmProtect

A simple, lightweight Minecraft server plugin that protects your farmland from being trampled by players and mobs.

## Requirements

- Paper (or a fork) 1.21+ — built and tested against the 26.1.2 API
- Java 21+

> The Kotlin runtime is downloaded automatically by Paper's library loader, so the plugin jar stays only a few KB.

## Configuration

`config.yml`:

```yaml
# Prevent players from trampling farmland (bypassable with farmprotect.bypass).
protect-from-players: true

# Prevent mobs and other entities from trampling farmland.
protect-from-mobs: true

# Worlds where protection is DISABLED (case-insensitive).
disabled-worlds: []
```

## Commands

| Command | Description |
| --- | --- |
| `/farmprotect reload` (alias `/fp`) | Reload the configuration. |

## Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `farmprotect.bypass` | `false` | Holder can still trample farmland. |
| `farmprotect.admin` | `op` | Use `/farmprotect`. |

## Building

```bash
mvn package
```

The plugin jar is produced at `target/FarmProtect-<version>.jar`.
